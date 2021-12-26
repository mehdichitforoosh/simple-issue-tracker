package ag.pinguin.issuetracker.developer.validator;

import ag.pinguin.issuetracker.developer.domain.Developer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Developer Validator
 *
 * @author Mehdi Chitforoosh
 */
@Component
public class DeveloperValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Developer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Developer developer = (Developer) target;
        String name = developer.getName();
        if (!(StringUtils.hasLength(name) && name.length() >= 3 && name.length() <= 100)) {
            errors.rejectValue("name", "invalid");
        }
    }
}
