package ag.pinguin.issuetracker.issue.controller.json;


import ag.pinguin.issuetracker.issue.domain.Story;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Story Json Object
 *
 * @author Mehdi Chitforoosh
 */
public final class StoryJsonObject extends IssueJsonObject {

    private final Story.Status status;
    private final Integer estimatedPoint;

    @JsonCreator
    public StoryJsonObject(@JsonProperty(value = "id") Long id
            , @JsonProperty(value = "title") String title
            , @JsonProperty(value = "description") String description
            , @JsonProperty(value = "version") Long version
            , @JsonProperty(value = "status") Story.Status status
            , @JsonProperty(value = "estimatedPoint") Integer estimatedPoint) {
        super(id, title, description, Type.STORY, version);
        this.status = status;
        this.estimatedPoint = estimatedPoint;
    }

    public Story.Status getStatus() {
        return status;
    }

    public Integer getEstimatedPoint() {
        return estimatedPoint;
    }
}
