package ag.pinguin.issuetracker.issue.domain;

import ag.pinguin.issuetracker.developer.domain.Developer;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Issue Entity
 *
 * @author Mehdi Chitforoosh
 */
@Entity
@Table(name = "issues")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "issue_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long issueId;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "assigned_developer_id")
    private Developer assignedDeveloper;

    @Version
    private Long version;

    // Default Constructor
    public Issue() {
    }

    public Issue(Long issueId, String title, String description, LocalDateTime creationDate, Developer assignedDeveloper, Long version) {
        this.issueId = issueId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.assignedDeveloper = assignedDeveloper;
        this.version = version;
    }

    public Long getIssueId() {
        return issueId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Developer getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public Long getVersion() {
        return version;
    }
}
