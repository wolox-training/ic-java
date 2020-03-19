package wolox.training.exceptions;

public class UserIdMismatchException extends RuntimeException {

  public UserIdMismatchException(String errorMessage) {
    super(errorMessage);
  }
}
