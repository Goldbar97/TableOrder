package kang.tableorder.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception {

    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(e -> e.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))
        .oauth2Login(Customizer.withDefaults())
        .authorizeHttpRequests(e -> {
          e
              .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
              .requestMatchers("/signup", "/signin", "/code").permitAll()
              .requestMatchers("/cart/**").permitAll()
              .requestMatchers("/restaurants/**").permitAll()
              .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**",
                  "/v3/api-docs/**", "/webjars/**").permitAll()
              .anyRequest().authenticated();
        })
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}