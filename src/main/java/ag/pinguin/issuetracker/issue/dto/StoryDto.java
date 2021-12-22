package ag.pinguin.issuetracker.issue.dto;

import ag.pinguin.issuetracker.issue.domain.Story;

public class StoryDto extends IssueDto {

    private final Story.Status status;

    public StoryDto(String title, String description, Story.Status status) {
        super(title, description);
        this.status = status;
    }

    public StoryDto(Long issueId, String title, String description, Story.Status status) {
        super(issueId, title, description);
        this.status = status;
    }

    public Story.Status getStatus() {
        return status;
    }
}
