
package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BookRepository bookRepository;

  private Book book = new Book();

  @Before
  public void setUp() {
    book.setAuthor("Author");
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

  @Test(expected = BookNotFoundException.class)
  public void whenFindByWrongAuthor_thenExceptionIsThrown() {
    bookRepository.findByAuthor(book.getAuthor().concat("asdf"));
  }
}