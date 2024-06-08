package kang.tableorder.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {

    super(errorCode.getValue());
    this.errorCode = errorCode;
  }
}
