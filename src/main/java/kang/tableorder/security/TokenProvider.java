package kang.tableorder.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import kang.tableorder.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {

  private final String KEY_ROLES = "roles";
  private final String BEARER = "Bearer ";
  private final int TOKEN_EXPIRE_TIME = 1000 * 60 * 60;

  @Value("${spring.jwt.secret}")
  private String secretKey;

  public String generateToken(String email, List<UserRole> role) {

    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return Jwts.builder()
        .claim(KEY_ROLES, role)
        .subject(email)
        .issuedAt(now)
        .expiration(expirationDate)
        .signWith(getSignKey(secretKey), Jwts.SIG.HS512)
        .compact();
  }

  public String getEmail(String token) {

    return parseClaims(token).getSubject();
  }

  public List<UserRole> getRole(String token) {

    return (List<UserRole>) parseClaims(token).get(KEY_ROLES);
  }

  public boolean validateToken(String token) {

    if (!StringUtils.hasText(token)) {
      return false;
    }

    Claims claims = parseClaims(token);
    return !claims.getExpiration().before(new Date());
  }

  private SecretKey getSignKey(String secretKey) {

    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Claims parseClaims(String token) {

    try {
      if (token.startsWith(BEARER)) {
        token = token.substring(BEARER.length());
      }

      return Jwts.parser()
          .verifyWith(getSignKey(secretKey))
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
