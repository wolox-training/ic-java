package wolox.training.exceptions;

public class BookIdMismatchException extends RuntimeException {
  public BookIdMismatchException(String errorMessage) {
    super(errorMessage);
  }
}