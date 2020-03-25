
package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  private User user = new User();

  @Before
  public void setUp() {
    user.setUsername("Username");
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

  @Test(expected = UserNotFoundException.class)
  public void whenFindByWrongUsername_thenExceptionIsThrown() {
    userRepository.findByUsername(user.getUsername().concat("asdf"));
  }
}