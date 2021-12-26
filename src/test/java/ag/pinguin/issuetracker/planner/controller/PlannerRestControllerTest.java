package ag.pinguin.issuetracker.planner.controller;

import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.planner.dto.AssignedStoryIssueDto;
import ag.pinguin.issuetracker.planner.service.PlannerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Planner Rest Controller Test
 *
 * @author Mehdi Chitforoosh
 */
@WebMvcTest(PlannerRestController.class)
public class PlannerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlannerService plannerService;

    @Test
    @DisplayName("Should get assigned story issues.")
    void shouldGetAssignedStoryIssues() throws Exception {

        // Arrange
        var assignedIssues1 = new ArrayList<AssignedStoryIssueDto>();
        assignedIssues1.add(new AssignedStoryIssueDto(1L, "Add a button", "Desc1", Story.Status.NEW, 12, "Eric"));
        assignedIssues1.add(new AssignedStoryIssueDto(2L, "Add a text", "Desc2", Story.Status.NEW, 12, "Alex"));
        assignedIssues1.add(new AssignedStoryIssueDto(3L, "Add a modal", "Desc3", Story.Status.NEW, 12, "Mehdi"));
        var assignedIssues2 = new ArrayList<AssignedStoryIssueDto>();
        assignedIssues2.add(new AssignedStoryIssueDto(4L, "Edit the function", "Desc4", Story.Status.NEW, 12, "Eric"));
        assignedIssues2.add(new AssignedStoryIssueDto(5L, "Delete the function", "Desc5", Story.Status.NEW, 12, "Alex"));
        assignedIssues2.add(new AssignedStoryIssueDto(6L, "Modify the class", "Desc6", Story.Status.NEW, 12, "Mehdi"));
        var list = new ArrayList<List<AssignedStoryIssueDto>>();
        list.add(assignedIssues1);
        list.add(assignedIssues2);

        doReturn(list).when(plannerService).getAssignedStoryIssues();

        // Act and Assert
        mockMvc.perform(get("/api/v1/planner")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0][0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0][0].title", equalTo("Add a button")))
                .andExpect(jsonPath("$.[0][0].status", equalTo("NEW")))
                .andExpect(jsonPath("$.[0][1].id", equalTo(2)))
                .andExpect(jsonPath("$.[0][1].title", equalTo("Add a text")))
                .andExpect(jsonPath("$.[0][1].status", equalTo("NEW")))
                .andExpect(jsonPath("$.[1][0].id", equalTo(4)))
                .andExpect(jsonPath("$.[1][0].title", equalTo("Edit the function")))
                .andExpect(jsonPath("$.[1][0].status", equalTo("NEW")))
                .andReturn();

        verify(plannerService).getAssignedStoryIssues();
    }

}
