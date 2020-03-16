package wolox.training.controllers;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;


@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private BookRepository bookRepository;

  @GetMapping("/greeting")
  public String greeting(@RequestParam(defaultValue = "Wolox") String name, Model model) {
    model.addAttribute("name", name);
    // The name "greeting" would not work
    return "hello";
  }


  @GetMapping
  public Iterable findAll() {
    return bookRepository.findAll();
  }

  @GetMapping("/{id}")
  public Book findOne(@PathVariable Long id) {
    try {
      return bookRepository.findById(id).orElseThrow(
          () -> new BookNotFoundException("Book not found for show")
      );
    } catch (BookNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found", ex);
    }
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Book create(@RequestBody Book book) {
    return bookRepository.save(book);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    try {
      bookRepository.findById(id).orElseThrow(
          () -> new BookNotFoundException("Book not found when deleting"));
      bookRepository.deleteById(id);
    } catch (BookNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found", ex);
    }
  }

  @PutMapping("/{id}")
  public Book updateBook(@NotNull @RequestBody Book book,
      @PathVariable Long id) {
    if (book.getId() != id) {
      throw new BookIdMismatchException("Book doesn't match id");
    }
    bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException("Book not found when updating"));
    return bookRepository.save(book);
  }
}