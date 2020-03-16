package wolox.training.exceptions;

public class BookNotFoundException extends RuntimeException {
  public BookNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}