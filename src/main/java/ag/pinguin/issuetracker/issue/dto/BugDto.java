package ag.pinguin.issuetracker.issue.dto;

import ag.pinguin.issuetracker.issue.domain.Bug;

public class BugDto extends IssueDto {

    private final Bug.Priority priority;
    private final Bug.Status status;

    public BugDto(String title, String description, Bug.Priority priority, Bug.Status status) {
        super(title, description);
        this.priority = priority;
        this.status = status;
    }

    public BugDto(Long issueId, String title, String description, Bug.Priority priority, Bug.Status status) {
        super(issueId, title, description);
        this.priority = priority;
        this.status = status;
    }

    public Bug.Priority getPriority() {
        return priority;
    }

    public Bug.Status getStatus() {
        return status;
    }
}
