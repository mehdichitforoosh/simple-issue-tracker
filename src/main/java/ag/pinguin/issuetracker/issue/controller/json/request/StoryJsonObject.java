package ag.pinguin.issuetracker.issue.controller.json.request;


import ag.pinguin.issuetracker.issue.domain.Story;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class StoryJsonObject extends IssueJsonObject {

    private final Story.Status status;

    @JsonCreator
    public StoryJsonObject(@JsonProperty(value = "title") String title
            , @JsonProperty(value = "description") String description
            , @JsonProperty(value = "status") Story.Status status) {
        super(title, description);
        this.status = status;
    }

    public Story.Status getStatus() {
        return status;
    }
}
