package ag.pinguin.issuetracker.planner.controller;

import ag.pinguin.issuetracker.common.exception.ValidationException;
import ag.pinguin.issuetracker.planner.controller.json.AssignedStoryIssueJsonObject;
import ag.pinguin.issuetracker.planner.service.PlannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Planner Rest Controller
 *
 * @author Mehdi Chitforoosh
 */
@RestController
@RequestMapping("/api/v1/planner")
public class PlannerRestController {

    private static final Logger logger = LoggerFactory.getLogger(PlannerRestController.class);

    @Autowired
    private PlannerService plannerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlannedIssues(@RequestParam(value = "maxPoint", required = false, defaultValue = "10") Integer maxPointPerDeveloper) {
        logger.debug("Get assigned story issues weekly ...");
        if (maxPointPerDeveloper < 10) {
            throw new ValidationException();
        }
        List<List<AssignedStoryIssueJsonObject>> list = plannerService.getAssignedStoryIssues(maxPointPerDeveloper).stream()
                .map(dtos -> dtos.stream()
                        .map(dto -> new AssignedStoryIssueJsonObject(dto.getId(), dto.getTitle(), dto.getDescription(), dto.getStatus(), dto.getEstimatedPoint(), dto.getAssignedDeveloper()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
