package ag.pinguin.issuetracker.issue.controller.json;


import ag.pinguin.issuetracker.issue.domain.Bug;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Bug Json Object
 *
 * @author Mehdi Chitforoosh
 */
public final class BugJsonObject extends IssueJsonObject {

    private final Bug.Priority priority;
    private final Bug.Status status;

    @JsonCreator
    public BugJsonObject(@JsonProperty(value = "id") Long id
            , @JsonProperty(value = "title") String title
            , @JsonProperty(value = "description") String description
            , @JsonProperty(value = "version") Long version
            , @JsonProperty(value = "priority") Bug.Priority priority
            , @JsonProperty(value = "status") Bug.Status status) {
        super(id, title, description, Type.BUG, version);
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
