package ag.pinguin.issuetracker.issue.repository;

import ag.pinguin.issuetracker.developer.repository.DeveloperRepository;
import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.util.DbTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Issue Repository Test
 *
 * @author Mehdi Chitforoosh
 */
@DataJpaTest(properties = {"test.reset.sql.template=ALTER TABLE %s ALTER COLUMN issue_id RESTART WITH 1"})
public class IssueRepositoryTest {

    private static final Clock CLOCK = Clock.fixed(Instant.parse("2021-12-10T10:15:30.24Z"), ZoneId.of("UTC"));

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        DbTestUtil.resetAutoIncrementColumns(applicationContext, "issues");
    }

    @Test
    @DisplayName("Should save an issue then find it by id.")
    void shouldSaveAnIssueThenFindById() {

        // Arrange
        var newStory = Story.builder()
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .setCreationDate(LocalDateTime.now(CLOCK))
                .build();

        // Act
        issueRepository.save(newStory);
        var foundedIssue = issueRepository.findById(1L);

        // Assert
        assertNotNull(foundedIssue);
        assertTrue(foundedIssue.isPresent());
        assertEquals(1L, foundedIssue.get().getIssueId());
        assertEquals(0L, foundedIssue.get().getVersion());
        assertEquals("Add a button", foundedIssue.get().getTitle());
        assertEquals(LocalDateTime.now(CLOCK), foundedIssue.get().getCreationDate());
    }

    @Test
    @DisplayName("Should save an issue then find it by id.")
    void shouldReturnExceptionWhenFieldsAreNull() {

        // Arrange
        var newStory = Story.builder()
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .build();

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> issueRepository.save(newStory));
    }

    @Test
    @DisplayName("Should update an issue and increment version then find it by id.")
    void shouldUpdateAnIssueAndIncrementVersionThenFindById() {

        // Arrange
        var newStory = Story.builder()
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .setCreationDate(LocalDateTime.now(CLOCK))
                .build();
        var updatedStory = Story.builder()
                .setIssueId(1L)
                .setTitle("Add a button with blue color")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .setVersion(0L)
                .build();

        // Act
        issueRepository.save(newStory);
        issueRepository.saveAndFlush(updatedStory);
        var foundedIssue = issueRepository.findById(1L);

        // Assert
        assertNotNull(foundedIssue);
        assertTrue(foundedIssue.isPresent());
        assertEquals(1L, foundedIssue.get().getIssueId());
        assertEquals(1L, foundedIssue.get().getVersion());
        assertEquals("Add a button with blue color", foundedIssue.get().getTitle());
    }

    @Test
    @DisplayName("Should delete an issue then find it by id.")
    void shouldDeleteAnIssueThenFindById() {

        // Arrange
        var newStory = Story.builder()
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .setCreationDate(LocalDateTime.now(CLOCK))
                .build();

        // Act
        issueRepository.save(newStory);
        issueRepository.deleteById(1L);
        var foundedIssue = issueRepository.findById(1L);

        // Assert
        assertNotNull(foundedIssue);
        assertFalse(foundedIssue.isPresent());
    }

    @Test
    @DisplayName("Should find issues by title.")
    void shouldFindIssuessByTitle() {

        // Arrange
        var story1 = Story.builder().setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(8).setCreationDate(LocalDateTime.now(CLOCK)).build();
        var story2 = Story.builder().setTitle("Add a modal").setStatus(Story.Status.NEW).setEstimatedPoint(8).setCreationDate(LocalDateTime.now(CLOCK)).build();
        var story3 = Story.builder().setTitle("Add a function").setStatus(Story.Status.NEW).setEstimatedPoint(8).setCreationDate(LocalDateTime.now(CLOCK)).build();
        var story4 = Story.builder().setTitle("Add a validator").setStatus(Story.Status.NEW).setEstimatedPoint(8).setCreationDate(LocalDateTime.now(CLOCK)).build();
        var bug1 = Bug.builder().setTitle("Modify the button").setStatus(Bug.Status.NEW).setPriority(Bug.Priority.CRITICAL).setCreationDate(LocalDateTime.now(CLOCK)).build();
        var bug2 = Bug.builder().setTitle("Modify the function").setStatus(Bug.Status.NEW).setPriority(Bug.Priority.CRITICAL).setCreationDate(LocalDateTime.now(CLOCK)).build();

        // Act
        issueRepository.save(story1);
        issueRepository.save(story2);
        issueRepository.save(story3);
        issueRepository.save(story4);
        issueRepository.save(bug1);
        issueRepository.save(bug2);
        Page<Issue> page = issueRepository.findByTitleContaining("Mo", PageRequest.of(0, 5));

        // Assert
        assertNotNull(page);
        assertEquals(2, page.getNumberOfElements());
        assertEquals("Modify the button", page.getContent().get(0).getTitle());
        assertEquals("Modify the function", page.getContent().get(1).getTitle());
    }

    @Test
    @Sql("/addAssignedDeveloper.sql")
    @DisplayName("Should add an assigned developer to an issue then find it by id.")
    void shouldAddAnAssignedDeveloperToAnIssueThenFindById() {

        // Arrange And Act
        issueRepository.addAssignedDeveloper(1L, 2L);
        var foundedIssue = issueRepository.findById(1L);

        // Assert
        assertNotNull(foundedIssue);
        assertTrue(foundedIssue.isPresent());
        assertNotNull(foundedIssue.get().getAssignedDeveloper());
        assertEquals(2L, foundedIssue.get().getAssignedDeveloper().getId());
        assertEquals("Alex Schober", foundedIssue.get().getAssignedDeveloper().getName());
    }

    @Test
    @Sql("/removeAssignedDeveloper.sql")
    @DisplayName("Should remove an assigned developer from an issue then find it by id.")
    void shouldRemoveAnAssignedDeveloperToAnIssueThenFindById() {

        // Arrange And Act
        issueRepository.removeAssignedDeveloper(2L);
        var foundedIssue1 = issueRepository.findById(1L);
        var foundedIssue2 = issueRepository.findById(2L);

        // Assert
        assertNotNull(foundedIssue1);
        assertNotNull(foundedIssue2);
        assertTrue(foundedIssue1.isPresent());
        assertTrue(foundedIssue2.isPresent());
        assertNull(foundedIssue1.get().getAssignedDeveloper());
        assertNull(foundedIssue2.get().getAssignedDeveloper());
    }

}
