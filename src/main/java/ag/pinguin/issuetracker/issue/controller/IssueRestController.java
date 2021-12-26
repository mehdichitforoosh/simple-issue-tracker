package ag.pinguin.issuetracker.issue.controller;

import ag.pinguin.issuetracker.common.exception.ValidationException;
import ag.pinguin.issuetracker.common.json.PageJsonObject;
import ag.pinguin.issuetracker.issue.controller.json.*;
import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.issue.service.IssueService;
import ag.pinguin.issuetracker.issue.validator.IssueValidator;
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

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Issue Rest Controller
 *
 * @author Mehdi Chitforoosh
 */
@RestController
@RequestMapping("/api/v1/issues")
public class IssueRestController {

    private static final Logger logger = LoggerFactory.getLogger(IssueRestController.class);

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueValidator issueValidator;

    @Autowired
    private Clock clock;

    @PostMapping(value = "/stories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueIdJsonObject> createStory(@RequestBody final StoryJsonObject jsonObject) {
        logger.debug("Create a new story ...");
        var story = Story.builder()
                .setTitle(jsonObject.getTitle())
                .setDescription(jsonObject.getDescription())
                .setCreationDate(LocalDateTime.now(clock))
                .setStatus(jsonObject.getStatus())
                .setEstimatedPoint(jsonObject.getEstimatedPoint())
                .build();
        validate(story);
        var id = issueService.createStory(story);
        return ResponseEntity.status(HttpStatus.CREATED).body(new IssueIdJsonObject(id));
    }

    @PostMapping(value = "/bugs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBug(@RequestBody final BugJsonObject jsonObject) {
        logger.debug("Create a new bug ...");
        var bug = Bug.builder()
                .setTitle(jsonObject.getTitle())
                .setDescription(jsonObject.getDescription())
                .setCreationDate(LocalDateTime.now(clock))
                .setStatus(jsonObject.getStatus())
                .setPriority(jsonObject.getPriority())
                .build();
        validate(bug);
        var id = issueService.createBug(bug);
        return ResponseEntity.status(HttpStatus.CREATED).body(new IssueIdJsonObject(id));
    }

    @PutMapping(value = "/stories/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueVersionJsonObject> updateStory(@PathVariable Long id, @RequestBody final StoryJsonObject jsonObject) {
        logger.debug("Update a story with id: " + id);
        var story = Story.builder()
                .setIssueId(id)
                .setTitle(jsonObject.getTitle())
                .setDescription(jsonObject.getDescription())
                .setStatus(jsonObject.getStatus())
                .setEstimatedPoint(jsonObject.getEstimatedPoint())
                .setVersion(jsonObject.getVersion())
                .build();
        validate(story);
        var version = issueService.updateStory(story);
        return ResponseEntity.ok(new IssueVersionJsonObject(version));
    }

    @PutMapping(value = "/bugs/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueVersionJsonObject> updateBug(@PathVariable Long id, @RequestBody final BugJsonObject jsonObject) {
        logger.debug("Update a bug with id: " + id);
        var bug = Bug.builder()
                .setIssueId(id)
                .setTitle(jsonObject.getTitle())
                .setDescription(jsonObject.getDescription())
                .setStatus(jsonObject.getStatus())
                .setPriority(jsonObject.getPriority())
                .setVersion(jsonObject.getVersion())
                .build();
        validate(bug);
        var version = issueService.updateBug(bug);
        return ResponseEntity.ok(new IssueVersionJsonObject(version));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueJsonObject> findById(@PathVariable Long id) {
        logger.debug("Get an issue with id: " + id);
        var issue = issueService.findById(id);
        IssueJsonObject issueJsonObject = null;
        if (issue instanceof Story) {
            var story = (Story) issue;
            issueJsonObject = new StoryJsonObject(story.getIssueId(), story.getTitle(), story.getDescription(), story.getVersion(), story.getStatus(), story.getEstimatedPoint());
        } else if (issue instanceof Bug) {
            var bug = (Bug) issue;
            issueJsonObject = new BugJsonObject(bug.getIssueId(), bug.getTitle(), bug.getDescription(), bug.getVersion(), bug.getPriority(), bug.getStatus());
        }
        return ResponseEntity.ok(issueJsonObject);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageJsonObject> findByTitle(@RequestParam(value = "startIndex", required = false, defaultValue = "0") final Integer startIndex
            , @RequestParam(value = "itemsPerPage", required = false, defaultValue = "10") final Integer itemsPerPage
            , @RequestParam(value = "title", required = false, defaultValue = "") final String title) {
        Pageable pageable = Utils.getPageable(startIndex, itemsPerPage);
        Page<Issue> page = issueService.findByTitle(title, pageable);
        List<IssueJsonObject> list = page.get()
                .map(issue -> {
                    IssueJsonObject.Type type = IssueJsonObject.Type.BUG;
                    if (issue instanceof Story) {
                        type = IssueJsonObject.Type.STORY;
                    }
                    return new IssueJsonObject(issue.getIssueId(), issue.getTitle(), issue.getDescription(), type, issue.getVersion());
                }).collect(Collectors.toList());
        PageJsonObject<IssueJsonObject> pageJsonObject = new PageJsonObject<>(page.getTotalElements(), list);
        return ResponseEntity.ok(pageJsonObject);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        logger.debug("Delete an issue with id: " + id);
        issueService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void validate(Issue issue) {
        Assert.notNull(issue, "Issue should not be null.");
        BeanPropertyBindingResult results = new BeanPropertyBindingResult(issue, "issue");
        issueValidator.validate(issue, results);
        if (results.hasErrors()) {
            System.out.println(results);
            throw new ValidationException();
        }
    }
}
