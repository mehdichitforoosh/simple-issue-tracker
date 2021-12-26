package ag.pinguin.issuetracker.issue.controller;

import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.issue.service.IssueService;
import ag.pinguin.issuetracker.issue.validator.IssueValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Issue Rest Controller Test
 *
 * @author Mehdi Chitforoosh
 */
@WebMvcTest(IssueRestController.class)
public class IssueRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    @MockBean
    private IssueValidator issueValidator;

    @Test
    @DisplayName("Should create a story and return id.")
    void shouldCreateAStoryAndReturnId() throws Exception {

        // Arrange
        doReturn(1L).when(issueService).createStory(isA(Story.class));
        doNothing().when(issueValidator).validate(isA(Issue.class), isA(Errors.class));
        String content = "{\"title\":\"Add a button\",\"status\":\"NEW\",\"estimatedPoint\": 8}";

        // Act and Assert
        mockMvc.perform(post("/api/v1/issues/stories")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andReturn();

        verify(issueService).createStory(isA(Story.class));
        verify(issueValidator).validate(isA(Issue.class), isA(Errors.class));
    }

    @Test
    @DisplayName("Should create a bug and return id.")
    void shouldCreateABugAndReturnId() throws Exception {

        // Arrange
        doReturn(1L).when(issueService).createBug(isA(Bug.class));
        doNothing().when(issueValidator).validate(isA(Issue.class), isA(Errors.class));
        String content = "{\"title\":\"Add a button\",\"status\":\"NEW\",\"priority\": \"MAJOR\"}";

        // Act and Assert
        mockMvc.perform(post("/api/v1/issues/bugs")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andReturn();

        verify(issueService).createBug(isA(Bug.class));
        verify(issueValidator).validate(isA(Issue.class), isA(Errors.class));
    }

    @Test
    @DisplayName("Should return bad request with invalid input json.")
    public void shouldReturnBadRequestHttpStatusWithInvalidJson() throws Exception {

        // Arrange
        doAnswer(invocationOnMock -> {
            Errors errors = invocationOnMock.getArgument(1);
            errors.rejectValue("title", "invalid");
            return null;
        }).when(issueValidator).validate(isA(Issue.class), isA(Errors.class));
        String content = "{\"title\":\"A\"}";

        // Act and Assert
        mockMvc.perform(post("/api/v1/issues/stories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest())
                .andReturn();

        verifyNoInteractions(issueService);
        verify(issueValidator).validate(isA(Issue.class), isA(Errors.class));
    }

    @Test
    @DisplayName("Should update a story and return version.")
    void shouldUpdateAStoryAndReturnVersion() throws Exception {

        // Arrange
        doReturn(1L).when(issueService).updateStory(isA(Story.class));
        doNothing().when(issueValidator).validate(isA(Issue.class), isA(Errors.class));
        String content = "{\"title\":\"Add a button with blue color\",\"status\":\"NEW\",\"estimatedPoint\": 12,\"version\":0}";

        // Act and Assert
        mockMvc.perform(put("/api/v1/issues/stories/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version", equalTo(1)))
                .andReturn();

        verify(issueService).updateStory(isA(Story.class));
        verify(issueValidator).validate(isA(Issue.class), isA(Errors.class));
    }

    @Test
    @DisplayName("Should update a bug and return version.")
    void shouldUpdateABugAndReturnVersion() throws Exception {

        // Arrange
        doReturn(1L).when(issueService).updateBug(isA(Bug.class));
        doNothing().when(issueValidator).validate(isA(Issue.class), isA(Errors.class));
        String content = "{\"title\":\"Add a button with blue color\",\"status\":\"NEW\",\"priority\": \"MINOR\",\"version\":0}";

        // Act and Assert
        mockMvc.perform(put("/api/v1/issues/bugs/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version", equalTo(1)))
                .andReturn();

        verify(issueService).updateBug(isA(Bug.class));
        verify(issueValidator).validate(isA(Issue.class), isA(Errors.class));
    }

    @Test
    @DisplayName("Should get an issue by id and return json.")
    void shouldGetAnIssueByIdAndReturnJson() throws Exception {

        // Arrange
        doReturn(Story.builder().setIssueId(1L).setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L)
                .build()).when(issueService).findById(isA(Long.class));

        // Act and Assert
        mockMvc.perform(get("/api/v1/issues/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("Add a button")))
                .andExpect(jsonPath("$.status", equalTo("NEW")))
                .andExpect(jsonPath("$.estimatedPoint", equalTo(8)))
                .andExpect(jsonPath("$.version", equalTo(0)))
                .andReturn();

        verify(issueService).findById(isA(Long.class));
    }


    @Test
    @DisplayName("Should get a page of issues by name and return json.")
    void shouldGetAPageOfIssuesByNameAndReturnJson() throws Exception {

        // Arrange
        Pageable pageable = PageRequest.of(1, 5);
        var page = new PageImpl<Issue>(Arrays.asList(
                Story.builder().setIssueId(1L).setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
                Bug.builder().setIssueId(2L).setTitle("Add the breakpoint").setStatus(Bug.Status.NEW).setPriority(Bug.Priority.CRITICAL).setVersion(0L).build(),
                Story.builder().setIssueId(3L).setTitle("Add a text").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
                Story.builder().setIssueId(4L).setTitle("Add a modal").setStatus(Story.Status.NEW).setEstimatedPoint(8).setVersion(0L).build(),
                Bug.builder().setIssueId(5L).setTitle("Add the log function").setStatus(Bug.Status.NEW).setPriority(Bug.Priority.CRITICAL).setVersion(0L).build()
        ), pageable, 15);
        doReturn(page).when(issueService).findByTitle(isA(String.class), isA(Pageable.class));

        // Act and Assert
        mockMvc.perform(get("/api/v1/issues")
                .queryParam("startIndex", "0")
                .queryParam("itemsPerPage", "10")
                .queryParam("title", "A")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems", equalTo(15)))
                .andExpect(jsonPath("$.items[0].id", equalTo(1)))
                .andExpect(jsonPath("$.items[0].title", equalTo("Add a button")))
                .andExpect(jsonPath("$.items[0].version", equalTo(0)))
                .andExpect(jsonPath("$.items[1].id", equalTo(2)))
                .andExpect(jsonPath("$.items[1].title", equalTo("Add the breakpoint")))
                .andExpect(jsonPath("$.items[1].version", equalTo(0)))
                .andExpect(jsonPath("$.items[2].id", equalTo(3)))
                .andExpect(jsonPath("$.items[2].title", equalTo("Add a text")))
                .andExpect(jsonPath("$.items[2].version", equalTo(0)))
                .andReturn();

        verify(issueService).findByTitle(isA(String.class), isA(Pageable.class));
    }

    @Test
    @DisplayName("Should delete an issue by id.")
    void shouldDeleteAnIssueById() throws Exception {

        // Arrange
        doNothing().when(issueService).delete(isA(Long.class));

        // Act and Assert
        mockMvc.perform(delete("/api/v1/issues/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(issueService).delete(isA(Long.class));
    }


}
