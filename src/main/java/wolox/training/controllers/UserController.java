package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
@Api
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  @ApiOperation(value = "Find all Users")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of Users"),
  })
  public Iterable findAll() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Given an ID, find the user")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Found user"),
      @ApiResponse(code = 404, message = "User with that ID doesn't exist"),
  })
  public User findOne(@PathVariable Long id) {
    return userRepository.findById(id).orElseThrow(
        () -> new UserNotFoundException("User not found for show")
    );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a user")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created user"),
      @ApiResponse(code = 404, message = "User with that ID doesn't exist"),
  })
  public User create(@RequestBody User user) {
    return userRepository.save(user);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete a user")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Deleted user"),
      @ApiResponse(code = 404, message = "User with that ID doesn't exist"),
  })
  public void delete(@PathVariable Long id) {
    userRepository.findById(id).orElseThrow(
        () -> new UserNotFoundException("User not found when deleting"));
    userRepository.deleteById(id);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Update a user")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Updated user"),
      @ApiResponse(code = 404, message = "User with that ID doesn't exist"),
  })
  public User updateUser(@NotNull @RequestBody User user,
      @PathVariable Long id) {
    findUserByIdFromParams(user, id);
    return userRepository.save(user);
  }

  @DeleteMapping("/{id}//books/{bookId}")
  @ApiOperation(value = "Delete a book from a user's list")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Removed book from user's list"),
      @ApiResponse(code = 404, message = "User with that ID doesn't exist"),
  })
  public User removeBook(@NotNull @RequestBody User user, @NotNull @RequestBody Book book,
      @PathVariable Long id, @PathVariable Long bookId) {
    User userFromParams = findUserByIdFromParams(user, id);
    userFromParams.removeBook(book);
    return userRepository.save(userFromParams);
  }

  @PutMapping("/{id}/books")
  @ApiOperation(value = "Add a book to a user's list")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Added book to user's list"),
      @ApiResponse(code = 404, message = "User with that ID doesn't exist"),
  })
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
