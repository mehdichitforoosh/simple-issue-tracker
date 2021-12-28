package ag.pinguin.issuetracker.planner.service;

import ag.pinguin.issuetracker.developer.domain.Developer;
import ag.pinguin.issuetracker.developer.repository.DeveloperRepository;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.issue.repository.IssueRepository;
import ag.pinguin.issuetracker.planner.dto.AssignedStoryIssueDto;
import ag.pinguin.issuetracker.planner.dto.IssueGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Planner Service Implementation
 *
 * @author Mehdi Chitforoosh
 */
@Service
public class PlannerServiceImpl implements PlannerService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private IssueGroupingService issueGroupingService;

    @Override
    @Transactional
    public List<List<AssignedStoryIssueDto>> getAssignedStoryIssues(Integer maxPointPerDeveloper) {
        var issues = issueRepository.findByType(Issue.Type.STORY.name()); // find all issues by story type
        var developers = developerRepository.findAll(); // find all developers to assign the issues
        var issueGroups = issueGroupingService.findIssueGroups(issues, maxPointPerDeveloper); // find optimal issue groups with considering max point for each developer to assign to developers
        var weeklyAssignedIssues = assignDevelopersToIssues(developers, issueGroups); // Assign developers to issues
        return weeklyAssignedIssues;
    }

    /**
     * Assign optimal issue groups to developers sequentially
     *
     * @param developers  Developers list
     * @param issueGroups Each issue group contains issues that sum of the point values are less than or equal to max point value
     * @return
     */
    private List<List<AssignedStoryIssueDto>> assignDevelopersToIssues(List<Developer> developers, List<IssueGroupDto> issueGroups) {
        var numberOfDevelopers = developers.size();
        var assignedDeveloperIndex = 0;
        var list = new ArrayList<List<AssignedStoryIssueDto>>();
        var weeklyAssignedIssues = new ArrayList<AssignedStoryIssueDto>();
        Iterator<IssueGroupDto> iterator = issueGroups.iterator();
        while (iterator.hasNext()) {
            var issues = iterator.next().getIssues();
            var developer = developers.get(assignedDeveloperIndex);
            // Assign the developers to the issues
            for (Issue issue : issues) {
                var story = (Story) issue;
                // Update issues in database
                issueRepository.addAssignedDeveloper(issue.getIssueId(), developer.getId());
                var assignedStoryIssueDto = new AssignedStoryIssueDto(issue.getIssueId(), issue.getTitle(), issue.getDescription(), story.getStatus(), story.getEstimatedPoint(), developer.getName());
                weeklyAssignedIssues.add(assignedStoryIssueDto);
            }
            assignedDeveloperIndex++;
            if (assignedDeveloperIndex == 1) {
                // Add weekly issues
                list.add(weeklyAssignedIssues);
            } else if (assignedDeveloperIndex == numberOfDevelopers) {
                assignedDeveloperIndex = 0;
                weeklyAssignedIssues = new ArrayList<>();
            }
        }
        return list;
    }


}
