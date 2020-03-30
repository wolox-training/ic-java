package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;


@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

  @Autowired
  private BookRepository bookRepository;

  @GetMapping("/greeting")
  @ApiOperation(value = "Greet the user")
  public String greeting(@RequestParam(defaultValue = "Wolox") String name, Model model) {
    model.addAttribute("name", name);
    // The name "greeting" would not work
    return "hello";
  }

  @GetMapping
  @ApiOperation(value = "Find all books")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of books"),
  })
  public Iterable findAll() {
    return bookRepository.findAll();
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Given an id, find the book")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Found book"),
      @ApiResponse(code = 404, message = "Book with that ID doesn't exist")
  })
  public Book findOne(@PathVariable Long id) {
    return bookRepository.findById(id).orElseThrow(
        () -> new BookNotFoundException("Book not found for show")
    );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a book")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created book"),
  })
  public Book create(@RequestBody Book book) {
    return bookRepository.save(book);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete a book")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Deleted book"),
      @ApiResponse(code = 404, message = "Book with that ID doesn't exist")
  })
  public Book delete(@PathVariable Long id) {
   Book book = bookRepository.findById(id).orElseThrow(
        () -> new BookNotFoundException("Book not found when deleting"));
    bookRepository.deleteById(id);
    return book;
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Update a particular book")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Updated book"),
      @ApiResponse(code = 404, message = "Book with that ID doesn't exist")
  })
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
