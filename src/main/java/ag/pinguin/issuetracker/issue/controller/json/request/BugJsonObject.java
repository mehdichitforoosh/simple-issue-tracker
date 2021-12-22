package ag.pinguin.issuetracker.issue.controller.json.request;


import ag.pinguin.issuetracker.issue.domain.Bug;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class BugJsonObject extends IssueJsonObject {

    private final Bug.Priority priority;
    private final Bug.Status status;

    @JsonCreator
    public BugJsonObject(@JsonProperty(value = "title") String title
            , @JsonProperty(value = "description") String description
            , @JsonProperty(value = "priority") Bug.Priority priority
            , @JsonProperty(value = "status") Bug.Status status) {
        super(title, description);
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
