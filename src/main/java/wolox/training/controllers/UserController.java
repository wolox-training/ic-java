package wolox.training.controllers;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public Iterable findAll() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public User findOne(@PathVariable Long id) {
    try {
      return userRepository.findById(id).orElseThrow(
          () -> new UserNotFoundException("User not found for show")
      );
    } catch (UserNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", ex);
    }
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public User create(@RequestBody User user) {
    return userRepository.save(user);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    try {
      userRepository.findById(id).orElseThrow(
          () -> new UserNotFoundException("User not found when deleting"));
      userRepository.deleteById(id);
    } catch (UserNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", ex);
    }
  }

  @PutMapping("/{id}")
  public User updateUser(@NotNull @RequestBody User user,
      @PathVariable Long id) {
    User userFromParams = findUserByIdFromParams(user, id);
    return userRepository.save(userFromParams);
  }

  @DeleteMapping("/{id}//books/{bookId}")
  public User removeBook(@NotNull @RequestBody User user, @NotNull @RequestBody Book book,
      @PathVariable Long id, @PathVariable Long bookId) {
    User userFromParams = findUserByIdFromParams(user, id);
    userFromParams.removeBook(book);
    return userRepository.save(userFromParams);
  }

  @PutMapping("/{id}/books")
  public User addBook(@NotNull @RequestBody User user, @NotNull @RequestBody Book book,
      @PathVariable Long id) {
    User userFromParams = findUserByIdFromParams(user, id);
    userFromParams.addBook(book);
    return userRepository.save(userFromParams);
  }

  private User findUserByIdFromParams(User user, Long id) {
    if (user.getId() != id) {
      throw new UserIdMismatchException("User doesn't match id");
    }
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }
}
