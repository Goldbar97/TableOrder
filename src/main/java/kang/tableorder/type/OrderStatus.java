package kang.tableorder.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
  PENDING("Pending"),
  CANCELLED("Cancelled"),
  ACCEPTED("Accepted"),
  REJECTED("Rejected"),
  COMPLETED("Completed");

  private final String value;
}
