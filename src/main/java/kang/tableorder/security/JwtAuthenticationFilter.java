package kang.tableorder.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public final String TOKEN_HEADER = "Authorization";
  public final String TOKEN_PREFIX = "Bearer ";
  private final TokenProvider tokenProvider;
  private final AuthenticationService authenticationService;

  private String resolveTokenFromRequest(HttpServletRequest request) {
    String token = request.getHeader(TOKEN_HEADER);

    if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
      return token.substring(TOKEN_PREFIX.length());
    }

    return null;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = resolveTokenFromRequest(request);

    if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
      Authentication auth = authenticationService.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }
}