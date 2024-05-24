package kang.tableorder.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
  OWNER("ROLE_OWNER"),
  CUSTOMER("ROLE_CUSTOMER");

  private final String value;
}
