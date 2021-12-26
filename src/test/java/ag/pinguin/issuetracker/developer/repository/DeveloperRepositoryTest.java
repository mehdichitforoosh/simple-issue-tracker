package ag.pinguin.issuetracker.developer.repository;

import ag.pinguin.issuetracker.developer.domain.Developer;
import ag.pinguin.issuetracker.util.DbTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"test.reset.sql.template=ALTER TABLE %s ALTER COLUMN id RESTART WITH 1"})
public class DeveloperRepositoryTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DeveloperRepository developerRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        DbTestUtil.resetAutoIncrementColumns(applicationContext, "developers");
    }

    @Test
    @DisplayName("Should save a developer then find it by id.")
    void shouldSaveADeveloperThenFindById() {
        // Arrange
        var newDeveloper = Developer.builder()
                .setName("Simon")
                .build();

        // Act
        developerRepository.save(newDeveloper);
        Optional<Developer> foundedDeveloper = developerRepository.findById(1L);

        // Assert
        assertNotNull(foundedDeveloper);
        assertTrue(foundedDeveloper.isPresent());
        assertEquals(1L, foundedDeveloper.get().getId());
        assertEquals(0L, foundedDeveloper.get().getVersion());
        assertEquals("Simon", foundedDeveloper.get().getName());
    }

    @Test
    @DisplayName("Should update a developer and increment version then find it by id.")
    void shouldUpdateADeveloperAndIncrementVersionThenFindById() {
        // Arrange
        var newDeveloper = Developer.builder()
                .setName("Simon")
                .build();
        var updatedDeveloper = Developer.builder()
                .setId(1L)
                .setName("Sara")
                .setVersion(0L)
                .build();

        // Act
        developerRepository.save(newDeveloper);
        developerRepository.saveAndFlush(updatedDeveloper);
        Optional<Developer> foundedDeveloper = developerRepository.findById(1L);

        // Assert
        assertNotNull(foundedDeveloper);
        assertTrue(foundedDeveloper.isPresent());
        assertEquals(1L, foundedDeveloper.get().getId());
        assertEquals(1L, foundedDeveloper.get().getVersion());
        assertEquals("Sara", foundedDeveloper.get().getName());
    }

    @Test
    @DisplayName("Should delete a developer then find it by id.")
    void shouldDeleteADeveloperThenFindById() {
        // Arrange
        var newDeveloper = Developer.builder()
                .setName("Simon")
                .build();

        // Act
        developerRepository.save(newDeveloper);
        developerRepository.deleteById(1L);
        Optional<Developer> foundedDeveloper = developerRepository.findById(1L);

        // Assert
        assertNotNull(foundedDeveloper);
        assertFalse(foundedDeveloper.isPresent());
    }

    @Test
    @DisplayName("Should find developers by name.")
    void shouldFindDevelopersByName() {
        // Arrange
        var developer1 = Developer.builder().setName("Simon").build();
        var developer2 = Developer.builder().setName("Sara").build();
        var developer3 = Developer.builder().setName("Siegfried").build();
        var developer4 = Developer.builder().setName("Alexandra").build();
        var developer5 = Developer.builder().setName("Nora").build();
        var developer6 = Developer.builder().setName("Siegmund").build();

        // Act
        developerRepository.save(developer1);
        developerRepository.save(developer2);
        developerRepository.save(developer3);
        developerRepository.save(developer4);
        developerRepository.save(developer5);
        developerRepository.save(developer6);
        Page<Developer> page = developerRepository.findByNameContaining("S", PageRequest.of(0, 5));

        // Assert
        assertNotNull(page);
        assertEquals(4, page.getNumberOfElements());
        assertEquals("Simon", page.getContent().get(0).getName());
        assertEquals("Sara", page.getContent().get(1).getName());
        assertEquals("Siegfried", page.getContent().get(2).getName());
        assertEquals("Siegmund", page.getContent().get(3).getName());
    }

}
