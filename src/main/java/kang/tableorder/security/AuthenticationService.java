package kang.tableorder.security;

import kang.tableorder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService {

  private final TokenProvider tokenProvider;
  private final UserService userService;

  public Authentication getAuthentication(String jwt) {

    UserDetails userDetails = userService.loadUserByUsername(tokenProvider.getEmail(jwt));

    return new UsernamePasswordAuthenticationToken(
        userDetails, userDetails.getPassword(),
        userDetails.getAuthorities());
  }
}
