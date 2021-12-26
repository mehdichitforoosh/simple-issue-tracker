package ag.pinguin.issuetracker.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Validation Exception
 *
 * @author Mehdi Chitforoosh
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Validation exception")
public class ValidationException extends RuntimeException {

}
