package wolox.training.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserTest {

  private User user = new User();

  @Autowired
  private UserRepository userRepository;

  @Before
  public void setUp() {
    user.setUsername("Username");
    user.setBirthDate(LocalDate.parse("2020-04-04"));
    user.setName("El Nombre");
  }


  @Test
  public void whenSavingAValidUser_itSaves() {
    assertThat(user.getId()).isEqualTo(0L);
    userRepository.save(user);
    assertThat(userRepository.findById(user.getId()).get()).isEqualTo(user);
    assertThat(user.getId()).isNotEqualTo(0L);
  }


  @Test(expected = IllegalArgumentException.class)
  public void whenSavingAUserWithNoName_itFails() {
    user.setName("");
    userRepository.save(user);
  }

  @Test(expected = NullPointerException.class)
  public void whenSavingAUserWithNoBirthDate_itFails() {
    user.setBirthDate(null);
    userRepository.save(user);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSavingAUserWitAFutureBirthDate_itFails() {
    user.setBirthDate(LocalDate.now().plusDays(2));
    userRepository.save(user);
  }
}
