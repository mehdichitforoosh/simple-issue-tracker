package ag.pinguin.issuetracker.planner.service;

import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Issue Grouping Service Test
 *
 * @author Mehdi Chitforoosh
 */
public class IssueGroupingServiceTest {

    private IssueGroupingService testSubject = new DefaultIssueGroupingService();

    @Test
    @DisplayName("Should return optimal issue groups based on max point value.")
    void shouldReturnOptimalIssueGroupsBasedOnMaxPointValue() {

        //  Arrange
        List<Issue> issues = Arrays.asList(
                Story.builder().setIssueId(1L).setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
                Story.builder().setIssueId(2L).setTitle("Add the breakpoint").setStatus(Story.Status.NEW).setEstimatedPoint(2).setVersion(0L).build(),
                Story.builder().setIssueId(3L).setTitle("Add a text").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(3).setVersion(0L).build(),
                Story.builder().setIssueId(4L).setTitle("Add a modal").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(4).setVersion(0L).build(),
                Story.builder().setIssueId(5L).setTitle("Add a recursive function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(1).setVersion(0L).build(),
                Story.builder().setIssueId(6L).setTitle("Add the binary function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(3).setVersion(0L).build(),
                Story.builder().setIssueId(7L).setTitle("Add a update button").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(4).setVersion(0L).build(),
                Story.builder().setIssueId(8L).setTitle("Add the calculate function").setStatus(Story.Status.ESTIMATED).setEstimatedPoint(6).setVersion(0L).build()
        );

        // Act
        var result = testSubject.findIssueGroups(issues, 10);

        // Assert
        assertEquals(4, result.size());
        assertEquals(2, result.get(0).getIssues().size());
        assertEquals(3, result.get(1).getIssues().size());
        assertEquals(2, result.get(2).getIssues().size());
        assertEquals(1, result.get(3).getIssues().size());
        assertEquals(1, result.get(0).getIssues().get(0).getIssueId());
        assertEquals(2, result.get(0).getIssues().get(1).getIssueId());
        assertEquals(3, result.get(1).getIssues().get(0).getIssueId());
        assertEquals(4, result.get(1).getIssues().get(1).getIssueId());
        assertEquals(5, result.get(1).getIssues().get(2).getIssueId());
        assertEquals(6, result.get(2).getIssues().get(0).getIssueId());
        assertEquals(7, result.get(2).getIssues().get(1).getIssueId());
        assertEquals(8, result.get(3).getIssues().get(0).getIssueId());
    }
}
