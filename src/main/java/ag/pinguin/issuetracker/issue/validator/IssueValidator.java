package ag.pinguin.issuetracker.issue.validator;

import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Issue Validator
 *
 * @author Mehdi Chitforoosh
 */
@Component
public class IssueValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Issue.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Issue issue = (Issue) target;
        checkCommonProperties(issue.getTitle(), issue.getDescription(), errors);
        if (target instanceof Story) {
            Story story = (Story) target;
            checkStoryProperties(story.getStatus(), story.getEstimatedPoint(), errors);
        } else if (target instanceof Bug) {
            Bug bug = (Bug) target;
            checkBugProperties(bug.getStatus(), bug.getPriority(), errors);
        }
    }

    private void checkCommonProperties(String title, String description, Errors errors) {
        if (!(StringUtils.hasLength(title) && title.length() >= 3 && title.length() <= 200)) {
            errors.rejectValue("title", "invalid");
        }
        if (StringUtils.hasLength(description)) {
            if (!(description.length() <= 2000)) {
                errors.rejectValue("description", "invalid");
            }
        }
    }

    private void checkStoryProperties(Story.Status status, Integer estimatePoint, Errors errors) {
        if (status == null) {
            errors.rejectValue("status", "invalid");
        }
        if (!(estimatePoint != null && estimatePoint >= 1 && estimatePoint <= 10)) {
            errors.rejectValue("estimatedPoint", "invalid");
        }
    }

    private void checkBugProperties(Bug.Status status, Bug.Priority priority, Errors errors) {
        if (status == null) {
            errors.rejectValue("status", "invalid");
        }
        if (priority == null) {
            errors.rejectValue("priority", "invalid");
        }
    }
}
