package ag.pinguin.issuetracker.issue.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("BUG")
public class Bug extends Issue {

    @Enumerated(EnumType.STRING)
    @Column(name = "bug_priority")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "bug_status")
    private Status status;

    public Bug() {

    }

    public Bug(String title, String description, LocalDateTime creationDate, Priority priority, Status status) {
        super(title, description, creationDate);
        this.priority = priority;
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Priority {
        CRITICAL, MAJOR, MINOR
    }

    public enum Status {
        NEW, VERIFIED, RESOLVED
    }
}
