package ag.pinguin.issuetracker.issue.domain;

import ag.pinguin.issuetracker.developer.domain.Developer;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Bug Entity
 *
 * @author Mehdi Chitforoosh
 */
@Entity
@DiscriminatorValue("BUG")
public class Bug extends Issue {

    @Enumerated(EnumType.STRING)
    @Column(name = "bug_priority")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "bug_status")
    private Status status;

    // Default Constructor
    public Bug() {
    }

    public Bug(Long issueId, String title, String description, LocalDateTime creationDate, Developer assignedDeveloper, Long version, Priority priority, Status status) {
        super(issueId, title, description, creationDate, assignedDeveloper, version);
        this.priority = priority;
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }


    public static Builder builder() {
        return new Builder();
    }

    /**
     * Bug Builder
     */
    public static class Builder {

        private Long issueId;
        private String title;
        private String description;
        private LocalDateTime creationDate;
        private Developer assignedDeveloper;
        private Bug.Status status;
        private Bug.Priority priority;
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

        public Builder setStatus(Bug.Status status) {
            this.status = status;
            return this;
        }

        public Builder setPriority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder setVersion(Long version) {
            this.version = version;
            return this;
        }

        public Bug build() {
            return new Bug(this.issueId, this.title, this.description, this.creationDate, this.assignedDeveloper, this.version, this.priority, this.status);
        }
    }

    public enum Priority {
        CRITICAL, MAJOR, MINOR
    }

    public enum Status {
        NEW, VERIFIED, RESOLVED
    }
}
