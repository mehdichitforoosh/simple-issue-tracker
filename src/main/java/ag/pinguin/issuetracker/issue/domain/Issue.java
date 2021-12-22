package ag.pinguin.issuetracker.issue.domain;

import ag.pinguin.issuetracker.developer.domain.Developer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "issue_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "issue_id")
    private Long issueId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "assigned_developer_id")
    private Developer assignedDeveloper;

    public Issue() {

    }

    public Issue(String title, String description, LocalDateTime creationDate) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Developer getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public void setAssignedDeveloper(Developer assignedDeveloper) {
        this.assignedDeveloper = assignedDeveloper;
    }
}
