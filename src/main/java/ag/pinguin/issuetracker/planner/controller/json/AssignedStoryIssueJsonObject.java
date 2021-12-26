package ag.pinguin.issuetracker.planner.controller.json;

import ag.pinguin.issuetracker.issue.domain.Story;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Assigned Story Issue Json Object
 *
 * @author Mehdi Chitforoosh
 */
public final class AssignedStoryIssueJsonObject {

    @JsonProperty(value = "id")
    private final Long id;

    @JsonProperty(value = "title")
    private final String title;

    @JsonProperty(value = "description")
    private final String description;

    @JsonProperty(value = "status")
    private final Story.Status status;

    @JsonProperty(value = "estimatedPoint")
    private final Integer estimatedPoint;

    @JsonProperty(value = "assignedDeveloper")
    private final String assignedDeveloper;

    public AssignedStoryIssueJsonObject(Long id, String title, String description, Story.Status status, Integer estimatedPoint, String assignedDeveloper) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.estimatedPoint = estimatedPoint;
        this.assignedDeveloper = assignedDeveloper;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Story.Status getStatus() {
        return status;
    }

    public Integer getEstimatedPoint() {
        return estimatedPoint;
    }

    public String getAssignedDeveloper() {
        return assignedDeveloper;
    }
}
