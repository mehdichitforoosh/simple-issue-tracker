package ag.pinguin.issuetracker.issue.controller;

import ag.pinguin.issuetracker.issue.controller.json.request.BugJsonObject;
import ag.pinguin.issuetracker.issue.controller.json.request.StoryJsonObject;
import ag.pinguin.issuetracker.issue.controller.json.response.IssueIdJsonObject;
import ag.pinguin.issuetracker.issue.dto.BugDto;
import ag.pinguin.issuetracker.issue.dto.StoryDto;
import ag.pinguin.issuetracker.issue.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueRestController {

    private static final Logger logger = LoggerFactory.getLogger(IssueRestController.class);

    @Autowired
    private IssueService issueService;

    @PostMapping(value = "/stories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueIdJsonObject> create(@RequestBody final StoryJsonObject jsonObject) {
        logger.debug("Create new story ...");
        // TODO should be validated
        var storyDto = new StoryDto(jsonObject.getTitle(), jsonObject.getDescription(), jsonObject.getStatus());
        var id = issueService.createStory(storyDto);
        return ResponseEntity.ok(new IssueIdJsonObject(id));
    }

    @PostMapping(value = "/bugs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody final BugJsonObject jsonObject) {
        logger.debug("Create new bug ...");
        // TODO should be validated
        var bugDto = new BugDto(jsonObject.getTitle(), jsonObject.getDescription(), jsonObject.getPriority(), jsonObject.getStatus());
        var id = issueService.createBug(bugDto);
        return ResponseEntity.ok(new IssueIdJsonObject(id));
    }
}
