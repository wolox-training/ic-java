package wolox.training.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserControllerTest extends BaseControllerTest {

  @MockBean
  private UserRepository mockUserRepository;
  @MockBean
  private BookRepository mockBookRepository;


  private User user = new User();
  private Book book = new Book();
  private String userAsJson = "{\"id\":1,\"username\":\"Username\",\"name\":\"El Nombre\",\"birthDate\":\"2020-04-04\",\"books\":[{\"id\":0,\"author\":null,\"image\":null,\"title\":null,\"subtitle\":null,\"publisher\":null,\"year\":null,\"pages\":0,\"isbn\":null,\"users\":[]}]}";
  private String userAsJsonWithoutBooks = "{\"id\":1,\"username\":\"Username\",\"name\":\"El Nombre\",\"birthDate\":\"2020-04-04\"}";
  private String bookAsJson = "{\"id\":1,\"author\":\"Author\",\"image\":\"La imagen\",\"title\":\"El título\",\"subtitle\":\"El subtítulo\",\"publisher\":\"El publisher\",\"year\":\"1989\",\"pages\":250,\"isbn\":\"ISBN\"}";

  @Before
  public void setUp() {
    user.setUsername("Username");
    user.setBirthDate(LocalDate.parse("2020-04-04"));
    user.setName("El Nombre");
    user.setId(1);
    user.addBook(book);
  }

  @Test
  public void whenFindById_thenReturnUser() throws Exception {
    Mockito.when(mockUserRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.ofNullable(user));
    getAndExpect("/api/users/" + user.getId(), status().isOk(), userAsJson);
  }

  @Test
  public void whenFindAll_thenReturnUsers() throws Exception {
    Mockito.when(mockUserRepository.findAll()).thenReturn(Arrays.asList(user));
    getAndExpect("/api/users", status().isOk(), "[" + userAsJson + "]");
  }

  @Test
  public void whenDeletingAUser_thenReturnDeletedUser() throws Exception {
    Mockito.when(mockUserRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.ofNullable(user));
    deleteAndExpect("/api/users/" + user.getId(), status().isOk(), userAsJson);
  }


  @Test
  public void whenDeletingAUserThatDoesntExist_thenFail() throws Exception {
    deleteWithStatus("/api/users/500", status().isNotFound());
  }

  @Test
  public void whenDeletingAUser_thenCallTheDeleteMethod() throws Exception {
    Mockito.when(mockUserRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.ofNullable(user));
    deleteWithStatus("/api/users/" + user.getId(), status().isOk());
    verify(mockUserRepository, times(1)).deleteById(user.getId());
  }


  @Test
  public void whenCreatingAUser_thenCallTheSaveMethod() throws Exception {
    Mockito.when(mockUserRepository.save(user)).thenReturn(user);
    postWithStatus("/api/users/", userAsJsonWithoutBooks, status().isCreated());
    verify(mockUserRepository, times(1)).save(any(User.class));

  }

  @Test
  public void whenUpdatingAUser_thenCallTheSaveMethod() throws Exception {
    Mockito.when(mockUserRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.ofNullable(user));
    Mockito.when(mockUserRepository.save(user)).thenReturn(user);
    putWithStatus("/api/users/" + user.getId(), userAsJsonWithoutBooks, status().isOk());
    verify(mockUserRepository, times(1)).save(any(User.class));
  }


  @Test
  public void whenAddingABookToTheUser_thenCallTheSaveMethod() throws Exception {
    Mockito.when(mockUserRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.ofNullable(user));
    Mockito.when(mockUserRepository.save(user)).thenReturn(user);
    putWithStatus("/api/users/" + user.getId() + "/books", bookAsJson, status().isOk());
    verify(mockUserRepository, times(1)).save(any(User.class));
  }

  @Test
  public void whenDeletingABookFromTheUser_thenCallTheSaveMethod() throws Exception {
    Mockito.when(mockUserRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.ofNullable(user));

    Mockito.when(mockBookRepository.findById(book.getId()))
        .thenReturn(java.util.Optional.ofNullable(book));
    Mockito.when(mockUserRepository.save(user)).thenReturn(user);
    deleteWithStatus("/api/users/" + user.getId() + "/books/" + book.getId(), status().isOk());
    verify(mockUserRepository, times(1)).save(any(User.class));
  }

  @Test
  public void whenDeletingABookFromTheUserButItDoesntExist_throwError() throws Exception {
    user.setBooks(new ArrayList<>());
    Mockito.when(mockUserRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.ofNullable(user));
    Mockito.when(mockBookRepository.findById(book.getId()))
        .thenReturn(java.util.Optional.ofNullable(book));
    deleteWithStatus("/api/users/" + user.getId() + "/books/" + book.getId(),
        status().isNotFound());
    verify(mockUserRepository, times(0)).save(any(User.class));
  }

}
