package ag.pinguin.issuetracker.issue.domain;

import ag.pinguin.issuetracker.developer.domain.Developer;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Story Entity
 *
 * @author Mehdi Chitforoosh
 */
@Entity
@DiscriminatorValue("STORY")
public class Story extends Issue {

    @Enumerated(EnumType.STRING)
    @Column(name = "story_status")
    private Status status;

    @Column(name = "estimated_point")
    private Integer estimatedPoint;

    // Default Constructor
    public Story() {
    }

    public Story(Long issueId, String title, String description, LocalDateTime creationDate, Developer assignedDeveloper, Long version, Status status, Integer estimatedPoint) {
        super(issueId, title, description, creationDate, assignedDeveloper, version);
        this.status = status;
        this.estimatedPoint = estimatedPoint;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getEstimatedPoint() {
        return estimatedPoint;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Story Builder
     */
    public static class Builder {

        private Long issueId;
        private String title;
        private String description;
        private LocalDateTime creationDate;
        private Developer assignedDeveloper;
        private Status status;
        private Integer estimatedPoint;
        private Long version;

        public Builder setIssueId(Long issueId) {
            this.issueId = issueId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder setAssignedDeveloper(Developer assignedDeveloper) {
            this.assignedDeveloper = assignedDeveloper;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setEstimatedPoint(Integer estimatedPoint) {
            this.estimatedPoint = estimatedPoint;
            return this;
        }

        public Builder setVersion(Long version) {
            this.version = version;
            return this;
        }

        public Story build() {
            return new Story(this.issueId, this.title, this.description, this.creationDate, this.assignedDeveloper, this.version, this.status, this.estimatedPoint);
        }
    }

    public enum Status {
        NEW, ESTIMATED, COMPLETED
    }

}
