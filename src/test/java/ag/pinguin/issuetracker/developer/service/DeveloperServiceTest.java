package ag.pinguin.issuetracker.developer.service;

import ag.pinguin.issuetracker.common.exception.NotFoundException;
import ag.pinguin.issuetracker.developer.domain.Developer;
import ag.pinguin.issuetracker.developer.repository.DeveloperRepository;
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
public class DeveloperServiceTest {

    @InjectMocks
    private DeveloperServiceImpl testSubject;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private IssueRepository issueRepository;

    @Test
    @DisplayName("Should create a developer and return id.")
    void shouldCreateADeveloperAndReturnId() {

        // Arrange
        var newDeveloper = Developer.builder()
                .setName("Simon")
                .build();
        var savedDeveloper = Developer.builder()
                .setId(2L)
                .setName("Simon")
                .setVersion(0L)
                .build();
        doReturn(savedDeveloper).when(developerRepository).save(isA(Developer.class));

        // Act
        Long resultId = testSubject.create(newDeveloper);

        // Assert
        verify(developerRepository, times(1)).save(isA(Developer.class));
        assertEquals(2L, resultId);
    }

    @Test
    @DisplayName("Should update a developer and increment version.")
    void shouldUpdateADeveloperAndIncrementVersion() {

        // Arrange
        var developer = Developer.builder()
                .setId(1L)
                .setName("Simon")
                .setVersion(0L)
                .build();
        var savedDeveloper = Developer.builder()
                .setId(1L)
                .setName("Simon")
                .setVersion(1L)
                .build();
        doReturn(savedDeveloper).when(developerRepository).saveAndFlush(isA(Developer.class));

        // Act
        Long resultVersion = testSubject.update(developer);

        // Assert
        verify(developerRepository, times(1)).saveAndFlush(isA(Developer.class));
        assertEquals(1L, resultVersion);
    }

    @Test
    @DisplayName("Should delete a developer and remove from assigned issues.")
    void shouldDeleteDeveloperAndRemoveFromAssignedIssues() {

        // Arrange and Act
        testSubject.delete(1L);

        // Assert
        verify(developerRepository, times(1)).deleteById(1L);
        verify(issueRepository, times(1)).removeAssignedDeveloper(1L);
    }

    @Test
    @DisplayName("Should find a developer by id.")
    void shouldFindADeveloperById() {

        // Arrange
        var optionalDeveloper = Optional.of(Developer.builder()
                .setId(1L)
                .setName("Simon")
                .setVersion(0L)
                .build());
        doReturn(optionalDeveloper).when(developerRepository).findById(isA(Long.class));

        // Act
        var result = testSubject.findById(1L);

        // Assert
        verify(developerRepository, times(1)).findById(isA(Long.class));
        assertEquals(1L, result.getId());
        assertEquals("Simon", result.getName());
        assertEquals(0L, result.getVersion());
    }

    @Test
    @DisplayName("Should return exception when a developer is not found by id.")
    void shouldReturnExceptionWhenADeveloperIsNotFoundById() {

        // Arrange
        doReturn(Optional.ofNullable(null)).when(developerRepository).findById(isA(Long.class));

        // Act Assert
        assertThrows(NotFoundException.class, () -> testSubject.findById(1L));
        verify(developerRepository, times(1)).findById(isA(Long.class));
    }

    @Test
    @DisplayName("Should return page of developers by containing the name.")
    void shouldReturnPageOfDeveloperByNAme() {

        // Arrange
        Pageable pageable = PageRequest.of(1, 5);
        var page = new PageImpl<Developer>(Arrays.asList(
                Developer.builder().setId(1L).setName("Simon").setVersion(0L).build(),
                Developer.builder().setId(2L).setName("Sara").setVersion(0L).build(),
                Developer.builder().setId(3L).setName("Siegfried").setVersion(0L).build(),
                Developer.builder().setId(4L).setName("Seifred").setVersion(0L).build(),
                Developer.builder().setId(5L).setName("Selda").setVersion(0L).build()
        ), pageable, 15);

        doReturn(page).when(developerRepository).findByNameContaining(isA(String.class), isA(Pageable.class));

        // Act
        var result = testSubject.findByName("S", pageable);

        // Assert
        verify(developerRepository, times(1)).findByNameContaining(isA(String.class), isA(Pageable.class));
        assertEquals(15, result.getTotalElements());
        assertEquals(5, result.getSize());
        assertEquals(1, result.getNumber());
        assertEquals(5, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals("Simon", result.getContent().get(0).getName());
        assertEquals(2L, result.getContent().get(1).getId());
        assertEquals("Sara", result.getContent().get(1).getName());
    }
}
