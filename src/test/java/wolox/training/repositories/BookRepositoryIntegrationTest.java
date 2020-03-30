
package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BookRepository bookRepository;

  private Book book = new Book();

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
    entityManager.persist(book);
    entityManager.flush();
  }

  @Test
  public void whenFindById_thenReturnBook() {
    Book found = bookRepository.findById(book.getId()).get();
    assertThat(found.getId()).isEqualTo(book.getId());
  }

  @Test
  public void whenFindByAuthor_thenReturnBook() {
    Book found = bookRepository.findByAuthor(book.getAuthor());
    assertThat(found.getId()).isEqualTo(book.getId());
  }

  @Test
  public void whenFindByWrongAuthor_thenNothingIsReturned() {
    assertThat(bookRepository.findByAuthor(book.getAuthor().concat("asdf"))).isNull();
  }
}