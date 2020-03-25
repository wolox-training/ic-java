package wolox.training.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.configuration.IMockitoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;

public class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookRepository mockBookRepository;
  private Book book;

  @Before
  public void setUp() {
    book = new Book();
    book.setAuthor("Author");
  }

  @Test
  public void whenFindById_thenReturnBook() throws Exception {
    Mockito.when(mockBookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(book));
    getAndExpect("/api/books/1", status().isOk(), "asdf");
  }
  @Test
  public void whenFindAll_thenReturnBooks() throws Exception {
    Mockito.when(mockBookRepository.findAll()).thenReturn(Arrays.asList(book));
    getAndExpect("/api/books", status().isOk(), "asdf");
  }

  public void getAndExpect(String url, ResultMatcher status, String content) throws Exception {
    mockMvc.perform(get(url)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status)
        .andExpect(content().json(content));
  }

}
