package ag.pinguin.issuetracker.planner.dto;

import ag.pinguin.issuetracker.issue.domain.Issue;

import java.util.List;

/**
 * Issue Group Dto
 *
 * @author Mehdi Chitforoosh
 */
public final class IssueGroupDto {

    private final List<Issue> issues;

    public IssueGroupDto(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Issue> getIssues() {
        return issues;
    }
}
