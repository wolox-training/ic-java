package wolox.training.config;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {

    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    if (this.passwordMatchesDatabase(username, password)) {
      return new UsernamePasswordAuthenticationToken(
          username, password, new ArrayList<>()
      );
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  private boolean passwordMatchesDatabase(String username, String password) {
    User user = userRepository.findByUsername(username);
    return new BCryptPasswordEncoder().matches(user.getPassword(), password);
  }
}