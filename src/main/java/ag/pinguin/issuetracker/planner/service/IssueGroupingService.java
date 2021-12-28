package ag.pinguin.issuetracker.planner.service;

import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.planner.dto.IssueGroupDto;

import java.util.List;

/**
 * Issue Grouping Service Based On Max Point Value Per Developer
 *
 * @author Mehdi Chitforoosh
 */
public interface IssueGroupingService {

    List<IssueGroupDto> findIssueGroups(List<Issue> issues, Integer maxPointPerDeveloper);

}
