package wolox.training.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(BookController.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookControllerTest extends BaseControllerTest {

  @MockBean
  private BookRepository mockBookRepository;
  private Book book = new Book();
  private String bookAsJson = "{\"id\":1,"
      + "\"author\":\"Author\","
      + "\"image\":\"La imagen\","
      + "\"title\":\"El título\","
      + "\"subtitle\":\"El subtítulo\","
      + "\"publisher\":\"El publisher\","
      + "\"year\":\"1989\","
      + "\"pages\":250,"
      + "\"isbn\":\"ISBN\","
      + "\"users\":[]}";

  @Before
  public void setUp() {
    buildMvc();
    book.setAuthor("Author");
    book.setImage("La imagen");
    book.setIsbn("ISBN");
    book.setPages(250);
    book.setPublisher("El publisher");
    book.setSubtitle("El subtítulo");
    book.setTitle("El título");
    book.setYear("1989");
    book.setId(1L);
  }

  @WithMockUser(username="spring", password="password")
  @Test
  public void whenFindById_thenReturnBook() throws Exception {
    Mockito.when(mockBookRepository.findById(book.getId()))
        .thenReturn(java.util.Optional.ofNullable(book));
    getAndExpect("/api/books/" + book.getId(), status().isOk(), bookAsJson);
  }

  @WithMockUser(username="spring", password="password")
  @Test
  public void whenFindAll_thenReturnBooks() throws Exception {
    Mockito.when(mockBookRepository.findAll()).thenReturn(Arrays.asList(book));
    getAndExpect("/api/books", status().isOk(), "[" + bookAsJson + "]");
  }

  @WithMockUser(username="spring", password="password")
  @Test
  public void whenDeletingABook_thenReturnDeletedBook() throws Exception {
    Mockito.when(mockBookRepository.findById(book.getId()))
        .thenReturn(java.util.Optional.ofNullable(book));
    deleteAndExpect("/api/books/" + book.getId(), status().isOk(), bookAsJson);
  }

  @WithMockUser(username="spring", password="password")
  @Test
  public void whenDeletingABook_thenCallTheDeleteMethod() throws Exception {
    Mockito.when(mockBookRepository.findById(book.getId()))
        .thenReturn(java.util.Optional.ofNullable(book));
    deleteWithStatus("/api/books/" + book.getId(), status().isOk());
    verify(mockBookRepository, times(1)).deleteById(book.getId());

  }

  @Test
  public void whenCreatingABook_thenCallTheSaveMethod() throws Exception {
    Mockito.when(mockBookRepository.save(book)).thenReturn(book);
    postWithStatus("/api/books/", bookAsJson, status().isCreated());
    verify(mockBookRepository, times(1)).save(any(Book.class));

  }

  @WithMockUser(username="spring", password="password")
  @Test
  public void whenUpdatingABook_thenCallTheSaveMethod() throws Exception {
    Mockito.when(mockBookRepository.findById(book.getId()))
        .thenReturn(java.util.Optional.ofNullable(book));
    Mockito.when(mockBookRepository.save(book)).thenReturn(book);
    putWithStatus("/api/books/" + book.getId(), bookAsJson, status().isOk());
    verify(mockBookRepository, times(1)).save(any(Book.class));
  }

}
