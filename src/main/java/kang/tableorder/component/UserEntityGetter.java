package kang.tableorder.component;

import kang.tableorder.dto.UserDetailsDto;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserEntityGetter {

  public UserEntity getUserEntity() {

    if (!isGuest()) {
      UserDetailsDto userDetailsDto = (UserDetailsDto) SecurityContextHolder.getContext()
          .getAuthentication()
          .getPrincipal();

      return userDetailsDto.getUserEntity();
    }

    throw new CustomException(ErrorCode.NO_USER);
  }

  public boolean isGuest() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication == null || authentication instanceof AnonymousAuthenticationToken;
  }
}