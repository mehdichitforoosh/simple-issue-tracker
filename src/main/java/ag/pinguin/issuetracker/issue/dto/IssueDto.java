package ag.pinguin.issuetracker.issue.dto;

public abstract class IssueDto {

    private final Long issueId;
    private final String title;
    private final String description;

    public IssueDto(String title, String description) {
        this.issueId = null;
        this.title = title;
        this.description = description;
    }

    public IssueDto(Long issueId, String title, String description) {
        this.issueId = issueId;
        this.title = title;
        this.description = description;
    }

    public Long getIssueId() {
        return issueId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
