package ag.pinguin.issuetracker.planner.service;

import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.planner.dto.AssignedStoryIssueDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Planner Service Implementation
 *
 * @author Mehdi Chitforoosh
 */
@Service
public class PlannerServiceImpl implements PlannerService {

    @Override
    public List<List<AssignedStoryIssueDto>> getAssignedStoryIssues() {
        var assignedIssues1 = new ArrayList<AssignedStoryIssueDto>();
        assignedIssues1.add(new AssignedStoryIssueDto(1L, "Add a button", "Desc1", Story.Status.NEW, 12, "Eric"));
        assignedIssues1.add(new AssignedStoryIssueDto(2L, "Add a text", "Desc2", Story.Status.NEW, 12, "Alex"));
        assignedIssues1.add(new AssignedStoryIssueDto(3L, "Add a modal", "Desc3", Story.Status.NEW, 12, "Mehdi"));
        var assignedIssues2 = new ArrayList<AssignedStoryIssueDto>();
        assignedIssues2.add(new AssignedStoryIssueDto(4L, "Edit the function", "Desc4", Story.Status.NEW, 12, "Eric"));
        assignedIssues2.add(new AssignedStoryIssueDto(5L, "Delete the function", "Desc5", Story.Status.NEW, 12, "Alex"));
        assignedIssues2.add(new AssignedStoryIssueDto(6L, "Modify the class", "Desc6", Story.Status.NEW, 12, "Mehdi"));
        var list = new ArrayList<List<AssignedStoryIssueDto>>();
        list.add(assignedIssues1);
        list.add(assignedIssues2);
        return list;
    }
}
