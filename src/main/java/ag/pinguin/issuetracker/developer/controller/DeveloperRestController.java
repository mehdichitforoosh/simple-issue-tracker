package ag.pinguin.issuetracker.developer.controller;

import ag.pinguin.issuetracker.developer.controller.json.request.DeveloperJsonObject;
import ag.pinguin.issuetracker.developer.controller.json.response.DeveloperIdJsonObject;
import ag.pinguin.issuetracker.developer.dto.DeveloperDto;
import ag.pinguin.issuetracker.developer.service.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestController {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperRestController.class);

    @Autowired
    private DeveloperService developerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeveloperIdJsonObject> create(@RequestBody final DeveloperJsonObject jsonObject) {
        logger.debug("Create new developer ...");
        // TODO should be validated
        var developerDto = new DeveloperDto(jsonObject.getName());
        var id = developerService.create(developerDto);
        return ResponseEntity.ok(new DeveloperIdJsonObject(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeveloperIdJsonObject> update(@PathVariable Long id, @RequestBody final DeveloperJsonObject jsonObject) {
        logger.debug("Update developer ...");
        // TODO should be validated
        var developerDto = new DeveloperDto(id, jsonObject.getName());
        developerService.updateBasic(developerDto);
        return ResponseEntity.ok(new DeveloperIdJsonObject(id));
    }
}
