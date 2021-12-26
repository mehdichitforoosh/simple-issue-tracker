package ag.pinguin.issuetracker.developer.controller;

import ag.pinguin.issuetracker.developer.domain.Developer;
import ag.pinguin.issuetracker.developer.service.DeveloperService;
import ag.pinguin.issuetracker.developer.validator.DeveloperValidator;
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
 * Developer Rest Controller Test
 *
 * @author Mehdi Chitforoosh
 */
@WebMvcTest(DeveloperRestController.class)
public class DeveloperRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeveloperService developerService;

    @MockBean
    private DeveloperValidator developerValidator;

    @Test
    @DisplayName("Should create a developer and return id.")
    void shouldCreateADeveloperAndReturnId() throws Exception {

        // Arrange
        doReturn(1L).when(developerService).create(isA(Developer.class));
        doNothing().when(developerValidator).validate(isA(Developer.class), isA(Errors.class));
        String content = "{\"name\":\"Eric Schultz\"}";

        // Act and Assert
        mockMvc.perform(post("/api/v1/developers")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andReturn();

        verify(developerService).create(isA(Developer.class));
        verify(developerValidator).validate(isA(Developer.class), isA(Errors.class));
    }

    @Test
    @DisplayName("Should return bad request with invalid input json.")
    public void shouldReturnBadRequestHttpStatusWithInvalidJson() throws Exception {

        // Arrange
        doAnswer(invocationOnMock -> {
            Errors errors = invocationOnMock.getArgument(1);
            errors.rejectValue("name", "invalid");
            return null;
        }).when(developerValidator).validate(isA(Developer.class), isA(Errors.class));
        String content = "{\"name\":\"A\"}";

        // Act and Assert
        mockMvc.perform(post("/api/v1/developers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest())
                .andReturn();

        verifyNoInteractions(developerService);
        verify(developerValidator).validate(isA(Developer.class), isA(Errors.class));
    }

    @Test
    @DisplayName("Should update a developer and return version.")
    void shouldUpdateADeveloperAndReturnVersion() throws Exception {

        // Arrange
        doReturn(1L).when(developerService).update(isA(Developer.class));
        doNothing().when(developerValidator).validate(isA(Developer.class), isA(Errors.class));
        String content = "{\"name\":\"Eric Schultz\",\"version\":0}";

        // Act and Assert
        mockMvc.perform(put("/api/v1/developers/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version", equalTo(1)))
                .andReturn();

        verify(developerService).update(isA(Developer.class));
        verify(developerValidator).validate(isA(Developer.class), isA(Errors.class));
    }

    @Test
    @DisplayName("Should get a developer by id and return json.")
    void shouldGetADeveloperByIdAndReturnJson() throws Exception {

        // Arrange
        doReturn(Developer.builder().setId(1L).setName("Eric Schultz").setVersion(0L).build()).when(developerService).findById(isA(Long.class));

        // Act and Assert
        mockMvc.perform(get("/api/v1/developers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Eric Schultz")))
                .andExpect(jsonPath("$.version", equalTo(0)))
                .andReturn();

        verify(developerService).findById(isA(Long.class));
    }

    @Test
    @DisplayName("Should get a page of developers by name and return json.")
    void shouldGetAPageOfDevelopersByNameAndReturnJson() throws Exception {

        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        var page = new PageImpl<Developer>(Arrays.asList(
                Developer.builder().setId(1L).setName("Simon").setVersion(0L).build(),
                Developer.builder().setId(2L).setName("Sara").setVersion(0L).build(),
                Developer.builder().setId(3L).setName("Siegfried").setVersion(0L).build(),
                Developer.builder().setId(4L).setName("Seifred").setVersion(0L).build(),
                Developer.builder().setId(5L).setName("Selda").setVersion(0L).build()
        ));
        doReturn(page).when(developerService).findByName(isA(String.class), isA(Pageable.class));

        // Act and Assert
        mockMvc.perform(get("/api/v1/developers")
                .queryParam("startIndex", "0")
                .queryParam("itemsPerPage", "10")
                .queryParam("name", "S")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems", equalTo(5)))
                .andExpect(jsonPath("$.items[0].id", equalTo(1)))
                .andExpect(jsonPath("$.items[0].name", equalTo("Simon")))
                .andExpect(jsonPath("$.items[0].version", equalTo(0)))
                .andExpect(jsonPath("$.items[1].id", equalTo(2)))
                .andExpect(jsonPath("$.items[1].name", equalTo("Sara")))
                .andExpect(jsonPath("$.items[1].version", equalTo(0)))
                .andExpect(jsonPath("$.items[4].id", equalTo(5)))
                .andExpect(jsonPath("$.items[4].name", equalTo("Selda")))
                .andExpect(jsonPath("$.items[4].version", equalTo(0)))
                .andReturn();

        verify(developerService).findByName(isA(String.class), isA(Pageable.class));
    }

    @Test
    @DisplayName("Should delete a developer by id.")
    void shouldDeleteADeveloperById() throws Exception {

        // Arrange
        doNothing().when(developerService).delete(isA(Long.class));

        // Act and Assert
        mockMvc.perform(delete("/api/v1/developers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(developerService).delete(isA(Long.class));
    }


}
