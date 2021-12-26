package ag.pinguin.issuetracker.developer.validator;

import ag.pinguin.issuetracker.developer.domain.Developer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Developer Validator Test
 *
 * @author Mehdi Chitforoosh
 */
public class DeveloperValidatorTest {

    private static DeveloperValidator testSubject;

    @BeforeAll
    static void setUp() {
        testSubject = new DeveloperValidator();
    }

    @Test
    @DisplayName("Should support developer class to validate.")
    void shouldSupportsDeveloperClass() {

        // Arrange and Act
        boolean result = testSubject.supports(Developer.class);

        // Assert
        assertEquals(true, result);
    }

    @Test
    @DisplayName("Should return no errors when developer's name is valid.")
    void shouldReturnNoErrorsWhenDeveloperNameIsValid() {

        // Arrange
        var developer = Developer.builder()
                .setName("Simon")
                .build();
        BeanPropertyBindingResult results = spy(new BeanPropertyBindingResult(developer, "developer"));

        // Act
        testSubject.validate(developer, results);

        // Assert
        verifyNoInteractions(results);
        assertFalse(results.hasErrors());
    }

    @ParameterizedTest
    @ArgumentsSource(NameArgumentsProvider.class)
    @DisplayName("Should return errors when developer's name is empty.")
    void shouldReturnErrorsWhenDeveloperNameIsInvalid(String name) {

        // Arrange
        var developer = Developer.builder()
                .setName(name)
                .build();
        BeanPropertyBindingResult results = spy(new BeanPropertyBindingResult(developer, "developer"));

        // Act
        testSubject.validate(developer, results);

        // Assert
        verify(results, times(1)).rejectValue("name", "invalid");
        assertTrue(results.hasErrors());
    }

    static class NameArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of((String) null),
                    Arguments.of(""),
                    Arguments.of("AB"),
                    Arguments.of("0XpI5vFym4fnOASZc3r22HE87nMWkA1FGNTFC6YgAv1qTl6LwbYyPZeLOk5T56iBAJKfoxC4mCEznsd07Q1lLsBTPHH5EbojfeFbC")
            );
        }
    }
}
