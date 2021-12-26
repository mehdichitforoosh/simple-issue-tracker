package ag.pinguin.issuetracker.planner.dto;

import ag.pinguin.issuetracker.issue.domain.Story;

/**
 * Assigned Story Issue Dto
 *
 * @author Mehdi Chitforoosh
 */
public final class AssignedStoryIssueDto {

    private final Long id;
    private final String title;
    private final String description;
    private final Story.Status status;
    private final Integer estimatedPoint;
    private final String assignedDeveloper;

    public AssignedStoryIssueDto(Long id, String title, String description, Story.Status status, Integer estimatedPoint, String assignedDeveloper) {
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
