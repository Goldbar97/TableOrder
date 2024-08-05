package kang.tableorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kang.tableorder.dto.UserDto;
import kang.tableorder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 인증번호 발급
  @Operation(summary = "인증번호 발급", description = "인증번호를 발급합니다.")
  @PostMapping("/code")
  public ResponseEntity<String> generateCode(@RequestBody @Valid UserDto.Code.Request request) {

    userService.generateCode(request);

    return ResponseEntity.ok("발급 완료");
  }

  // 회원가입
  @Operation(summary = "회원가입", description = "이메일과 비밀번호로 회원가입합니다.")
  @PostMapping("/signup")
  public ResponseEntity<?> signUp(
      @Valid @RequestBody UserDto.SignUp.Request request) {

    UserDto.SignUp.Response signedUp = userService.signUp(request);

    return ResponseEntity.ok(signedUp);
  }

  // 로그인
  @Operation(summary = "로그인", description = "회원가입했던 이메일과 비밀번호로 로그인하고 토큰을 발급받습니다.")
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(
      @Valid @RequestBody UserDto.SignIn.Request request) {

    UserDto.SignIn.Response signedIn = userService.signIn(request);

    return ResponseEntity.ok(signedIn);
  }

  // 회원 READ
  @Operation(summary = "회원 정보 조회", description = "발급받은 토큰과 비밀번호로 자신을 조회합니다.")
  @GetMapping("/user")
  public ResponseEntity<?> readUser(
      @RequestHeader("Authorization") String header,
      @Valid @RequestBody UserDto.Read.Request request) {

    UserDto.Read.Response info = userService.readUser(request);

    return ResponseEntity.ok(info);
  }

  // 회원 UPDATE
  @Operation(summary = "회원 정보 수정", description = "토큰과 비밀번호, 새 비밀번호, 새 별명, 새 전화번호를 입력합니다.")
  @PutMapping("/user")
  public ResponseEntity<?> updateUser(
      @RequestHeader("Authorization") String header,
      @Valid @RequestBody UserDto.Update.Request request) {

    UserDto.Update.Response updated = userService.updateUser(request);

    return ResponseEntity.ok(updated);
  }

  // 회원 DELETE
  @Operation(summary = "회원 탈퇴", description = "토큰과 비밀번호를 입력하고 회원을 탈퇴합니다.")
  @DeleteMapping("/user")
  public ResponseEntity<?> deleteUser(
      @RequestHeader("Authorization") String header,
      @Valid @RequestBody UserDto.Delete.Request request) {

    userService.deleteUser(request);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}