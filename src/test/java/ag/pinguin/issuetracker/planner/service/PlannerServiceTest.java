package ag.pinguin.issuetracker.planner.service;

import ag.pinguin.issuetracker.developer.domain.Developer;
import ag.pinguin.issuetracker.developer.repository.DeveloperRepository;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.issue.repository.IssueRepository;
import ag.pinguin.issuetracker.planner.dto.IssueGroupDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Planner Service Test
 *
 * @author Mehdi Chitforoosh
 */
@ExtendWith(MockitoExtension.class)
public class PlannerServiceTest {

    private final List<Issue> ISSUES = Arrays.asList(
            Story.builder().setIssueId(1L).setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
            Story.builder().setIssueId(2L).setTitle("Add the breakpoint").setStatus(Story.Status.NEW).setEstimatedPoint(2).setVersion(0L).build(),
            Story.builder().setIssueId(3L).setTitle("Add a text").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(3).setVersion(0L).build(),
            Story.builder().setIssueId(4L).setTitle("Add a modal").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(4).setVersion(0L).build(),
            Story.builder().setIssueId(5L).setTitle("Add a recursive function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(1).setVersion(0L).build(),
            Story.builder().setIssueId(6L).setTitle("Add the binary function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(3).setVersion(0L).build(),
            Story.builder().setIssueId(7L).setTitle("Add a update button").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(4).setVersion(0L).build(),
            Story.builder().setIssueId(8L).setTitle("Add the calculate function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(6).setVersion(0L).build()
    );

    private final List<Developer> DEVELOPERS = Arrays.asList(
            Developer.builder().setId(1L).setName("Mehdi Chitforoosh").setVersion(0L).build(),
            Developer.builder().setId(2L).setName("Simon Schober").setVersion(0L).build(),
            Developer.builder().setId(3L).setName("Alex Schultz").setVersion(0L).build()
    );

    @InjectMocks
    private PlannerServiceImpl testSubject;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private IssueGroupingService issueGroupingService;

    @Test
    @DisplayName("Should return assigned issues weekly based on max point value 10")
    void shouldReturnAssignedIssuesWeeklyBasedOMaxPointValue10() {

        // Arrange
        var issueGroupDtos = Arrays.asList(
                new IssueGroupDto(Arrays.asList(
                        Story.builder().setIssueId(1L).setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
                        Story.builder().setIssueId(2L).setTitle("Add the breakpoint").setStatus(Story.Status.NEW).setEstimatedPoint(2).setVersion(0L).build()
                )),
                new IssueGroupDto(Arrays.asList(
                        Story.builder().setIssueId(3L).setTitle("Add a text").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(3).setVersion(0L).build(),
                        Story.builder().setIssueId(4L).setTitle("Add a modal").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(4).setVersion(0L).build(),
                        Story.builder().setIssueId(5L).setTitle("Add a recursive function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(1).setVersion(0L).build()
                )),
                new IssueGroupDto(Arrays.asList(
                        Story.builder().setIssueId(6L).setTitle("Add the binary function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(3).setVersion(0L).build(),
                        Story.builder().setIssueId(7L).setTitle("Add a update button").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(4).setVersion(0L).build()
                )),
                new IssueGroupDto(Arrays.asList(
                        Story.builder().setIssueId(8L).setTitle("Add the calculate function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(6).setVersion(0L).build()
                ))
        );
        doReturn(ISSUES).when(issueRepository).findByType(Issue.Type.STORY.name());
        doReturn(DEVELOPERS).when(developerRepository).findAll();
        doReturn(issueGroupDtos).when(issueGroupingService).findIssueGroups(isA(List.class), isA(Integer.class));
        doNothing().when(issueRepository).addAssignedDeveloper(isA(Long.class), isA(Long.class));

        // Act
        var result = testSubject.getAssignedStoryIssues(10);

        // Assert
        assertEquals(2, result.size());
        assertEquals(7, result.get(0).size());
        assertEquals(1, result.get(1).size());
        assertEquals("Mehdi Chitforoosh", result.get(0).get(0).getAssignedDeveloper());
        assertEquals("Mehdi Chitforoosh", result.get(0).get(1).getAssignedDeveloper());
        assertEquals("Simon Schober", result.get(0).get(2).getAssignedDeveloper());
        assertEquals("Simon Schober", result.get(0).get(3).getAssignedDeveloper());
        assertEquals("Simon Schober", result.get(0).get(4).getAssignedDeveloper());
        assertEquals("Alex Schultz", result.get(0).get(5).getAssignedDeveloper());
        assertEquals("Alex Schultz", result.get(0).get(6).getAssignedDeveloper());
        assertEquals("Mehdi Chitforoosh", result.get(1).get(0).getAssignedDeveloper());
    }

    @Test
    @DisplayName("Should return assigned issues weekly based on max point value 15")
    void shouldReturnAssignedIssuesWeeklyBasedOMaxPointValue15() {

        // Arrange
        var issueGroupDtos = Arrays.asList(
                new IssueGroupDto(Arrays.asList(
                        Story.builder().setIssueId(1L).setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
                        Story.builder().setIssueId(2L).setTitle("Add the breakpoint").setStatus(Story.Status.NEW).setEstimatedPoint(2).setVersion(0L).build(),
                        Story.builder().setIssueId(3L).setTitle("Add a text").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(3).setVersion(0L).build()
                )),
                new IssueGroupDto(Arrays.asList(
                        Story.builder().setIssueId(4L).setTitle("Add a modal").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(4).setVersion(0L).build(),
                        Story.builder().setIssueId(5L).setTitle("Add a recursive function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(1).setVersion(0L).build(),
                        Story.builder().setIssueId(6L).setTitle("Add the binary function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(3).setVersion(0L).build(),
                        Story.builder().setIssueId(7L).setTitle("Add a update button").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(4).setVersion(0L).build()
                )),
                new IssueGroupDto(Arrays.asList(
                        Story.builder().setIssueId(8L).setTitle("Add the calculate function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(6).setVersion(0L).build()
                ))
        );
        doReturn(ISSUES).when(issueRepository).findByType(Issue.Type.STORY.name());
        doReturn(DEVELOPERS).when(developerRepository).findAll();
        doReturn(issueGroupDtos).when(issueGroupingService).findIssueGroups(isA(List.class), isA(Integer.class));
        doNothing().when(issueRepository).addAssignedDeveloper(isA(Long.class), isA(Long.class));

        // Act
        var result = testSubject.getAssignedStoryIssues(15);

        // Assert
        assertEquals(1, result.size());
        assertEquals(8, result.get(0).size());
        assertEquals("Mehdi Chitforoosh", result.get(0).get(0).getAssignedDeveloper());
        assertEquals("Mehdi Chitforoosh", result.get(0).get(1).getAssignedDeveloper());
        assertEquals("Mehdi Chitforoosh", result.get(0).get(2).getAssignedDeveloper());
        assertEquals("Simon Schober", result.get(0).get(3).getAssignedDeveloper());
        assertEquals("Simon Schober", result.get(0).get(4).getAssignedDeveloper());
        assertEquals("Simon Schober", result.get(0).get(5).getAssignedDeveloper());
        assertEquals("Simon Schober", result.get(0).get(6).getAssignedDeveloper());
        assertEquals("Alex Schultz", result.get(0).get(7).getAssignedDeveloper());
    }
}
