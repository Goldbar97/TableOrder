package kang.tableorder.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class UserDetailsDto implements UserDetails {

  private final UserEntity userEntity;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();

    for (UserRole role : userEntity.getRole()) {
      authorities.add(new SimpleGrantedAuthority(role.getValue()));
    }

    return authorities;
  }

  @Override
  public String getPassword() {
    return userEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return userEntity.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
