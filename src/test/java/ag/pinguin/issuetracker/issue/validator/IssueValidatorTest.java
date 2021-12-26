package ag.pinguin.issuetracker.issue.validator;

import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Developer Validator Test
 *
 * @author Mehdi Chitforoosh
 */
public class IssueValidatorTest {

    private static IssueValidator testSubject;

    @BeforeAll
    static void setUp() {
        testSubject = new IssueValidator();
    }

    @Test
    @DisplayName("Should support issue class to validate.")
    void shouldSupportsIssueClass() {

        // Arrange and Act
        boolean result = testSubject.supports(Issue.class);

        // Assert
        assertEquals(true, result);
    }

    @Test
    @DisplayName("Should return no errors when story issue is valid.")
    void shouldReturnNoErrorsWhenStoryIssueIsValid() {

        // Arrange
        var story = Story.builder()
                .setTitle("Add a button")
                .setStatus(Story.Status.NEW)
                .setEstimatedPoint(10)
                .build();
        BeanPropertyBindingResult results = spy(new BeanPropertyBindingResult(story, "issue"));

        // Act
        testSubject.validate(story, results);

        // Assert
        verifyNoInteractions(results);
        assertFalse(results.hasErrors());
    }

    @ParameterizedTest
    @ArgumentsSource(StoryArgumentsProvider.class)
    @DisplayName("Should return errors when story is invalid.")
    void shouldReturnErrorsWhenStoryIsInvalid(Story st) {

        // Arrange
        var story = Story.builder()
                .setTitle(st.getTitle())
                .setStatus(st.getStatus())
                .setEstimatedPoint(st.getEstimatedPoint())
                .build();
        BeanPropertyBindingResult results = spy(new BeanPropertyBindingResult(story, "issue"));

        // Act
        testSubject.validate(story, results);

        // Assert
        assertTrue(results.hasErrors());
    }

    @Test
    @DisplayName("Should return no errors when bug issue is valid.")
    void shouldReturnNoErrorsWhenBugIssueIsValid() {

        // Arrange
        var bug = Bug.builder()
                .setTitle("Add a button")
                .setStatus(Bug.Status.NEW)
                .setPriority(Bug.Priority.CRITICAL)
                .build();
        BeanPropertyBindingResult results = spy(new BeanPropertyBindingResult(bug, "issue"));

        // Act
        testSubject.validate(bug, results);

        // Assert
        verifyNoInteractions(results);
        assertFalse(results.hasErrors());
    }

    @ParameterizedTest
    @ArgumentsSource(BugArgumentsProvider.class)
    @DisplayName("Should return errors when bug is invalid.")
    void shouldReturnErrorsWhenBugIsInvalid(Bug bg) {

        // Arrange
        var bug = Bug.builder()
                .setTitle(bg.getTitle())
                .setStatus(bg.getStatus())
                .setPriority(bg.getPriority())
                .build();
        BeanPropertyBindingResult results = spy(new BeanPropertyBindingResult(bug, "issue"));

        // Act
        testSubject.validate(bug, results);

        // Assert
        assertTrue(results.hasErrors());
    }

    static class StoryArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(Story.builder().setTitle("A").build()),
                    Arguments.of(Story.builder().setTitle("Add a button").setStatus(null).build()),
                    Arguments.of(Story.builder().setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(0).build()),
                    Arguments.of(Story.builder().setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(-1).build()),
                    Arguments.of(Story.builder().setTitle("Add a button").setStatus(Story.Status.NEW).setEstimatedPoint(101).build())
            );
        }
    }

    static class BugArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(Bug.builder().setTitle("A").build()),
                    Arguments.of(Bug.builder().setTitle("Modify the function").setStatus(null).build()),
                    Arguments.of(Bug.builder().setTitle("Modify the function").setStatus(null).setPriority(null).build()),
                    Arguments.of(Bug.builder().setTitle("Modify the function").setStatus(Bug.Status.NEW).setPriority(null).build())
            );
        }
    }
}
