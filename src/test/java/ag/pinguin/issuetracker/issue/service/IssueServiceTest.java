package ag.pinguin.issuetracker.issue.service;

import ag.pinguin.issuetracker.common.exception.NotFoundException;
import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.issue.repository.IssueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @InjectMocks
    private IssueServiceImpl testSubject;

    @Mock
    private IssueRepository issueRepository;

    @Test
    @DisplayName("Should create a story issue and return id.")
    void shouldCreateAStoryIssueAndReturnId() {

        // Arrange
        var newStory = Story.builder()
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .build();
        var savedStory = Story.builder()
                .setIssueId(2L)
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .setVersion(0L)
                .build();

        doReturn(savedStory).when(issueRepository).save(isA(Issue.class));

        // Act
        Long resultId = testSubject.createStory(newStory);

        // Assert
        verify(issueRepository, times(1)).save(isA(Issue.class));
        assertEquals(2L, resultId);
    }

    @Test
    @DisplayName("Should create a bug issue and return id.")
    void shouldCreateABugIssueAndReturnId() {

        // Arrange
        var newBug = Bug.builder()
                .setTitle("Modify the function")
                .setStatus(Bug.Status.NEW)
                .setPriority(Bug.Priority.CRITICAL)
                .build();
        var savedBug = Bug.builder()
                .setIssueId(2L)
                .setTitle("Modify the function")
                .setStatus(Bug.Status.NEW)
                .setPriority(Bug.Priority.CRITICAL)
                .setVersion(0L)
                .build();

        doReturn(savedBug).when(issueRepository).save(isA(Issue.class));

        // Act
        Long resultId = testSubject.createBug(newBug);

        // Assert
        verify(issueRepository, times(1)).save(isA(Issue.class));
        assertEquals(2L, resultId);
    }

    @Test
    @DisplayName("Should update a story and increment version.")
    void shouldUpdateAStoryAndIncrementVersion() {

        // Arrange
        var story = Story.builder()
                .setIssueId(1L)
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .setVersion(0L)
                .build();
        var savedStory = Story.builder()
                .setIssueId(1L)
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .setVersion(1L)
                .build();

        doReturn(savedStory).when(issueRepository).saveAndFlush(isA(Issue.class));

        // Act
        Long resultVersion = testSubject.updateStory(story);

        // Assert
        verify(issueRepository, times(1)).saveAndFlush(isA(Issue.class));
        assertEquals(1L, resultVersion);
    }

    @Test
    @DisplayName("Should update a bug and increment version.")
    void shouldUpdateABugAndIncrementVersion() {

        // Arrange
        var bug = Bug.builder()
                .setIssueId(1L)
                .setTitle("Modify the function")
                .setStatus(Bug.Status.NEW)
                .setPriority(Bug.Priority.CRITICAL)
                .setVersion(0L)
                .build();
        var savedBug = Bug.builder()
                .setIssueId(1L)
                .setTitle("Modify the function")
                .setStatus(Bug.Status.NEW)
                .setPriority(Bug.Priority.CRITICAL)
                .setVersion(1L)
                .build();

        doReturn(savedBug).when(issueRepository).saveAndFlush(isA(Issue.class));

        // Act
        Long resultVersion = testSubject.updateBug(bug);

        // Assert
        verify(issueRepository, times(1)).saveAndFlush(isA(Issue.class));
        assertEquals(1L, resultVersion);
    }

    @Test
    @DisplayName("Should delete the issue.")
    void shouldDeleteIssue() {

        // Arrange and Act
        testSubject.delete(1L);

        // Assert
        verify(issueRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should find an issue by id.")
    void shouldFindAnIssueById() {

        // Arrange
        var optionalStory = Optional.of(Story.builder()
                .setIssueId(1L)
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(8)
                .setVersion(0L)
                .build());
        doReturn(optionalStory).when(issueRepository).findById(isA(Long.class));

        // Act
        var result = testSubject.findById(1L);

        // Assert
        verify(issueRepository, times(1)).findById(isA(Long.class));
        assertEquals(1L, result.getIssueId());
        assertEquals("Add a button", result.getTitle());
        assertEquals(0L, result.getVersion());
    }

    @Test
    @DisplayName("Should return exception when an issue is not found by id.")
    void shouldReturnExceptionWhenAnIssueIsNotFoundById() {

        // Arrange
        doReturn(Optional.ofNullable(null)).when(issueRepository).findById(isA(Long.class));

        // Act Assert
        assertThrows(NotFoundException.class, () -> testSubject.findById(1L));
        verify(issueRepository, times(1)).findById(isA(Long.class));
    }

    @Test
    @DisplayName("Should return page of issues by containing the title.")
    void shouldReturnPageOfIssuesByTitle() {

        // Arrange
        Pageable pageable = PageRequest.of(1, 5);
        var page = new PageImpl<Issue>(Arrays.asList(
                Story.builder().setIssueId(1L).setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
                Bug.builder().setIssueId(2L).setTitle("Add the breakpoint").setStatus(Bug.Status.NEW).setPriority(Bug.Priority.CRITICAL).setVersion(0L).build(),
                Story.builder().setIssueId(3L).setTitle("Add a text").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
                Story.builder().setIssueId(4L).setTitle("Add a modal").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
                Bug.builder().setIssueId(5L).setTitle("Add the log function").setStatus(Bug.Status.NEW).setPriority(Bug.Priority.CRITICAL).setVersion(0L).build()
        ), pageable, 15);

        doReturn(page).when(issueRepository).findByTitleContaining(isA(String.class), isA(Pageable.class));

        // Act
        var result = testSubject.findByTitle("A", pageable);

        // Assert
        verify(issueRepository, times(1)).findByTitleContaining(isA(String.class), isA(Pageable.class));
        assertEquals(15, result.getTotalElements());
        assertEquals(5, result.getSize());
        assertEquals(1, result.getNumber());
        assertEquals(5, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getIssueId());
        assertEquals("Add a button", result.getContent().get(0).getTitle());
        assertEquals(2L, result.getContent().get(1).getIssueId());
        assertEquals("Add the breakpoint", result.getContent().get(1).getTitle());
    }
}
