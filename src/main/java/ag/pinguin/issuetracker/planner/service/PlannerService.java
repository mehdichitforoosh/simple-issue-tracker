package ag.pinguin.issuetracker.planner.service;

import ag.pinguin.issuetracker.planner.dto.AssignedStoryIssueDto;

import java.util.List;

/**
 * Planner Service
 *
 * @author Mehdi Chitforoosh
 */
public interface PlannerService {

    List<List<AssignedStoryIssueDto>> getAssignedStoryIssues();

}
