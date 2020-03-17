package wolox.training.exceptions;

public class BookAlreadyOwnedException extends RuntimeException {
  public BookAlreadyOwnedException(String errorMessage) {
    super(errorMessage);
  }
}
