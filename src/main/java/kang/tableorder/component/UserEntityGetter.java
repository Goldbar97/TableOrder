package kang.tableorder.component;

import kang.tableorder.dto.UserDetailsDto;
import kang.tableorder.entity.UserEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserEntityGetter {

  public UserEntity getUserEntity() {

    UserDetailsDto userDetailsDto = (UserDetailsDto) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    return userDetailsDto.getUserEntity();
  }

  public boolean isGuest() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication instanceof AnonymousAuthenticationToken;
  }
}