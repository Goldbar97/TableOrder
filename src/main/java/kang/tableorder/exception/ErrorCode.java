package kang.tableorder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  ALREADY_EXISTS_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다."),
  ALREADY_EXISTS_MENU(HttpStatus.CONFLICT, "이미 존재하는 메뉴입니다."),
  ALREADY_EXISTS_NICKNAME(HttpStatus.CONFLICT, "이미 사용중인 별명입니다."),
  ALREADY_EXISTS_RESTAURANT(HttpStatus.CONFLICT, "이미 등록한 매장이 존재합니다."),
  ALREADY_EXISTS_TABLES_NUMBER(HttpStatus.CONFLICT, "이미 사용중인 테이블 번호입니다."),
  ALREADY_EXISTS_TABLETS_MAC_ID(HttpStatus.CONFLICT, "이미 사용중인 태블릿 MAC 번호입니다."),
  ALREADY_USING_PASSWORD(HttpStatus.CONFLICT, "이미 사용중인 비밀번호입니다."),
  NOT_AVAILABLE(HttpStatus.LOCKED, "매장 사정으로 판매 일시 중지된 메뉴입니다."),
  NO_MENU(HttpStatus.NO_CONTENT, "없는 메뉴입니다."),
  NO_RESTAURANT(HttpStatus.NO_CONTENT, "매장을 먼저 등록해 주세요."),
  NO_TABLES(HttpStatus.NO_CONTENT, "없는 테이블입니다."),
  NO_USER(HttpStatus.NO_CONTENT, "없는 계정입니다."),
  WRONG_MENU(HttpStatus.BAD_REQUEST, "매장에 등록되지 않은 메뉴입니다."),
  WRONG_OWNER(HttpStatus.FORBIDDEN, "매장에 등록되지 않은 계정입니다."),
  WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "틀린 비밀번호입니다.");

  private final HttpStatus status;
  private final String value;
}
