package kang.tableorder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
  ALREADY_EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, "이미 사용중인 별명입니다."),
  NO_SUCH_USER(HttpStatus.BAD_REQUEST, "없는 계정입니다."),
  WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "틀린 비밀번호입니다.");

  private final HttpStatus status;
  private final String value;
}
