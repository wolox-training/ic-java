package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Param ID doesn't match object ID")
public class BookIdMismatchException extends RuntimeException {

  public BookIdMismatchException(String errorMessage) {
    super(errorMessage);
  }
}