package wolox.training.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookTest {

  private Book book = new Book();

  @Autowired
  private BookRepository bookRepository;

  @Before
  public void setUp() {
    book.setAuthor("Author");
    book.setImage("La imagen");
    book.setIsbn("ISBN");
    book.setPages(250);
    book.setPublisher("El publisher");
    book.setSubtitle("El subtítulo");
    book.setTitle("El título");
    book.setYear("1989");
  }


  @Test
  public void whenSavingAValidBook_itSaves() {
    assertThat(book.getId()).isEqualTo(0L);
    bookRepository.save(book);
    assertThat(bookRepository.findById(book.getId()).get()).isEqualTo(book);
    assertThat(book.getId()).isNotEqualTo(0L);
  }


  @Test(expected = IllegalArgumentException.class)
  public void whenSavingAnBookWithNoAuthor_itFails() {
    book.setAuthor("");
    bookRepository.save(book);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSavingAnBookWithNoYear_itFails() {
    book.setYear("");
    bookRepository.save(book);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSavingAnBookWithNoPages_itFails() {
    book.setPages(0);
    bookRepository.save(book);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenSavingAnBookWithNoTitle_itFails() {
    book.setTitle("");
    bookRepository.save(book);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenSavingAnBookWithNoImage_itFails() {
    book.setImage("");
    bookRepository.save(book);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenSavingAnBookWithNoSubtitle_itFails() {
    book.setSubtitle("");
    bookRepository.save(book);
  }
}
