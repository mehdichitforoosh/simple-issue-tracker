package ag.pinguin.issuetracker.issue.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("STORY")
public class Story extends Issue {

    @Enumerated(EnumType.STRING)
    @Column(name = "story_status")
    private Status status;

    public Story() {

    }

    public Story(String title, String description, LocalDateTime creationDate, Status status) {
        super(title, description, creationDate);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        NEW, ESTIMATED, COMPLETED
    }

}
