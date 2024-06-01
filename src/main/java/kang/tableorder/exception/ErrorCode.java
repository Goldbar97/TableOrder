package kang.tableorder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
  ALREADY_EXISTS_MENU(HttpStatus.BAD_REQUEST, "이미 존재하는 메뉴입니다."),
  ALREADY_EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, "이미 사용중인 별명입니다."),
  ALREADY_EXISTS_RESTAURANT(HttpStatus.BAD_REQUEST, "이미 등록한 매장이 존재합니다."),
  ALREADY_EXISTS_TABLES_NUMBER(HttpStatus.BAD_REQUEST, "이미 사용중인 테이블 번호입니다."),
  ALREADY_EXISTS_TABLETS_MAC_ID(HttpStatus.BAD_REQUEST, "이미 사용중인 태블릿 MAC 번호입니다."),
  NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "매장 사정으로 판매 일시 중지된 메뉴입니다."),
  NO_MENU(HttpStatus.BAD_REQUEST, "없는 메뉴입니다."),
  NO_RESTAURANT(HttpStatus.BAD_REQUEST, "매장을 먼저 등록해 주세요."),
  NO_TABLES(HttpStatus.BAD_REQUEST, "없는 테이블입니다."),
  NO_USER(HttpStatus.BAD_REQUEST, "없는 계정입니다."),
  WRONG_MENU(HttpStatus.BAD_REQUEST, "매장에 등록되지 않은 메뉴입니다."),
  WRONG_OWNER(HttpStatus.BAD_REQUEST, "매장에 등록되지 않은 계정입니다."),
  WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "틀린 비밀번호입니다.");

  private final HttpStatus status;
  private final String value;
}
