package ag.pinguin.issuetracker.developer.controller;

import ag.pinguin.issuetracker.common.exception.ValidationException;
import ag.pinguin.issuetracker.common.json.PageJsonObject;
import ag.pinguin.issuetracker.developer.controller.json.DeveloperIdJsonObject;
import ag.pinguin.issuetracker.developer.controller.json.DeveloperJsonObject;
import ag.pinguin.issuetracker.developer.controller.json.DeveloperVersionJsonObject;
import ag.pinguin.issuetracker.developer.domain.Developer;
import ag.pinguin.issuetracker.developer.service.DeveloperService;
import ag.pinguin.issuetracker.developer.validator.DeveloperValidator;
import ag.pinguin.issuetracker.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Developer Rest Controller
 *
 * @author Mehdi Chitforoosh
 */
@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestController {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperRestController.class);

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private DeveloperValidator developerValidator;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeveloperIdJsonObject> create(@RequestBody final DeveloperJsonObject jsonObject) {
        logger.debug("Create a new developer ...");
        var developer = Developer.builder()
                .setName(jsonObject.getName())
                .build();
        validate(developer);
        var id = developerService.create(developer);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DeveloperIdJsonObject(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeveloperVersionJsonObject> update(@PathVariable Long id, @RequestBody final DeveloperJsonObject jsonObject) {
        logger.debug("Update a developer with id: " + id);
        var developer = Developer.builder()
                .setId(id)
                .setName(jsonObject.getName())
                .setVersion(jsonObject.getVersion())
                .build();
        validate(developer);
        var version = developerService.update(developer);
        return ResponseEntity.ok(new DeveloperVersionJsonObject(version));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeveloperJsonObject> findById(@PathVariable Long id) {
        logger.debug("Get a developer with id: " + id);
        var developer = developerService.findById(id);
        return ResponseEntity.ok(new DeveloperJsonObject(id, developer.getName(), developer.getVersion()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageJsonObject> findByName(@RequestParam(value = "startIndex", required = false, defaultValue = "0") final Integer startIndex
            , @RequestParam(value = "itemsPerPage", required = false, defaultValue = "10") final Integer itemsPerPage
            , @RequestParam(value = "name", required = false, defaultValue = "") final String name) {
        Pageable pageable = Utils.getPageable(startIndex, itemsPerPage);
        Page<Developer> page = developerService.findByName(name, pageable);
        List<DeveloperJsonObject> list = page.get()
                .map(developer -> new DeveloperJsonObject(developer.getId(), developer.getName(), developer.getVersion()))
                .collect(Collectors.toList());
        PageJsonObject<DeveloperJsonObject> pageJsonObject = new PageJsonObject<>(page.getTotalElements(), list);
        return ResponseEntity.ok(pageJsonObject);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        logger.debug("Delete a developer with id: " + id);
        developerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void validate(Developer developer) {
        Assert.notNull(developer, "Developer should not be null.");
        BeanPropertyBindingResult results = new BeanPropertyBindingResult(developer, "developer");
        developerValidator.validate(developer, results);
        if (results.hasErrors()) {
            throw new ValidationException();
        }
    }
}
