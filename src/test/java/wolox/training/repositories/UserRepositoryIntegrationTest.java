
package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  private User user = new User();

  @Before
  public void setUp() {
    user.setUsername("Username");
    user.setBirthDate(LocalDate.now().minusDays(2));
    user.setName("El Nombre");
    user.setPassword("password");
    entityManager.persist(user);
    entityManager.flush();
  }

  @Test
  public void whenFindById_thenReturnUser() {
    User found = userRepository.findById(user.getId()).get();
    assertThat(found.getId()).isEqualTo(user.getId());
  }

  @Test
  public void whenFindByUsername_thenReturnUser() {
    User found = userRepository.findByUsername(user.getUsername());
    assertThat(found.getId()).isEqualTo(user.getId());
  }

  @Test
  public void whenFindByWrongAuthor_thenNothingIsReturned() {
    assertThat(userRepository.findByUsername(user.getUsername().concat("asdf"))).isNull();
  }
}