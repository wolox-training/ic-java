package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Param ID doesn't match object ID")
public class UserIdMismatchException extends RuntimeException {

  public UserIdMismatchException(String errorMessage) {
    super(errorMessage);
  }
}
