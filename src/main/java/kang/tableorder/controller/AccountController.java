package kang.tableorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kang.tableorder.dto.AccountDto;
import kang.tableorder.dto.AccountDto.GuestDeposit;
import kang.tableorder.dto.AccountDto.Read.Response;
import kang.tableorder.dto.AccountDto.UserDeposit;
import kang.tableorder.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "계좌", description = "계좌 관련 API")
@RestController
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  // 회원 계좌 생성
  @Operation(summary = "계좌 생성", description = "토큰을 받고 회원의 계좌를 생성합니다.")
  @PreAuthorize("hasRole('CUSTOMER')")
  @PostMapping("/user/account")
  public ResponseEntity<?> createAccount(
      @RequestHeader("Authorization") String header) {

    AccountDto.Create.Response saved = accountService.createAccount();

    return ResponseEntity.ok(saved);
  }

  // 회원 계좌 확인
  @Operation(summary = "계좌 조회", description = "토큰을 받고 회원의 계좌를 조회합니다.")
  @PreAuthorize("hasRole('CUSTOMER')")
  @GetMapping("/user/account")
  public ResponseEntity<?> readAccount(
      @RequestHeader("Authorization") String header) {

    AccountDto.Read.Response info = accountService.readAccount();

    return ResponseEntity.ok(info);
  }

  // 비회원 계좌 확인
  @Operation(summary = "비회원 계좌 조회", description = "매장ID, 테이블ID, 태블릿MAC ID를 받고 계좌를 조회합니다.")
  @GetMapping("/restaurants/{restaurantId}/tables/{tablesId}/account")
  public ResponseEntity<?> readAccount(
      @PathVariable Long restaurantId,
      @PathVariable Long tablesId,
      @Valid @RequestBody AccountDto.Read.Request form) {

    AccountDto.Read.Response info = accountService.readAccount(restaurantId, tablesId, form);

    return ResponseEntity.ok(info);
  }

  // 회원 입금
  @Operation(summary = "회원 입금", description = "토큰, 액수를 입력받고 입금합니다.")
  @PreAuthorize("hasRole('CUSTOMER')")
  @PutMapping("/user/account")
  public ResponseEntity<?> depositAccount(
      @RequestHeader("Authorization") String header,
      @Valid @RequestBody AccountDto.UserDeposit.Request form) {

    AccountDto.UserDeposit.Response updated = accountService.depositAccount(form);

    return ResponseEntity.ok(updated);
  }

  // 비회원 입금
  @Operation(summary = "비회원 입금", description = "매장ID, 테이블ID, 액수를 입력받고 입금합니다.")
  @PutMapping("/restaurants/{restaurantId}/tables/{tablesId}/account")
  public ResponseEntity<?> depositAccount(
      @PathVariable Long restaurantId,
      @PathVariable Long tablesId,
      @Valid @RequestBody AccountDto.GuestDeposit.Request form) {

    AccountDto.GuestDeposit.Response updated = accountService.depositAccount(restaurantId, tablesId, form);

    return ResponseEntity.ok(updated);
  }
}