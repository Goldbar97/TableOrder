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
  NOT_AVAILABLE(HttpStatus.NOT_ACCEPTABLE, "불가능한 요청입니다."),
  NOT_AVAILABLE_MENU(HttpStatus.LOCKED, "매장 사정으로 판매 일시 중지된 메뉴입니다."),
  NOT_AVAILABLE_ORDER(HttpStatus.NOT_ACCEPTABLE, "결제 불가능한 주문입니다."),
  NOT_ENOUGH_BALANCE(HttpStatus.NOT_ACCEPTABLE, "잔액이 부족합니다."),
  NO_ACCOUNT(HttpStatus.NOT_FOUND, "없는 계좌입니다."),
  NO_CART_ITEM(HttpStatus.NOT_FOUND, "없는 항목입니다."),
  NO_MENU(HttpStatus.NOT_FOUND, "없는 메뉴입니다."),
  NO_ORDER(HttpStatus.NOT_FOUND, "없는 주문입니다."),
  NO_RESTAURANT(HttpStatus.NOT_FOUND, "매장을 먼저 등록해 주세요."),
  NO_REVIEW(HttpStatus.NOT_FOUND, "없는 리뷰입니다."),
  NO_TABLES(HttpStatus.NOT_FOUND, "없는 테이블입니다."),
  NO_USER(HttpStatus.NOT_FOUND, "없는 계정입니다."),
  WRONG_CODE(HttpStatus.BAD_REQUEST, "잘못된 인증번호 입니다."),
  WRONG_MENU(HttpStatus.BAD_REQUEST, "매장에 등록되지 않은 메뉴입니다."),
  WRONG_OWNER(HttpStatus.FORBIDDEN, "매장에 등록되지 않은 계정입니다."),
  WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "틀린 비밀번호입니다.");

  private final HttpStatus status;
  private final String value;
}