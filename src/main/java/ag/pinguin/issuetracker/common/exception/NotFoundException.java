package ag.pinguin.issuetracker.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Not Found Exception
 *
 * @author Mehdi Chitforoosh
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not found exception")
public class NotFoundException extends RuntimeException {

}
