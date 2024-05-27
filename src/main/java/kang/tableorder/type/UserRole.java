package kang.tableorder.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
  OWNER("ROLE_OWNER"),
  CUSTOMER("ROLE_CUSTOMER");

  private final String value;
}
