package kang.tableorder.controller;

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

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 회원가입
  @PostMapping("/signup")
  public ResponseEntity<?> signUp(
      @Valid @RequestBody UserDto.SignUp.Request form) {

    UserDto.SignUp.Response signedUp = userService.signUp(form);

    return ResponseEntity.ok(signedUp);
  }

  // 로그인
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(
      @Valid @RequestBody UserDto.SignIn.Request form) {

    UserDto.SignIn.Response signedIn = userService.signIn(form);

    return ResponseEntity.ok(signedIn);
  }

  // 회원 READ
  @GetMapping("/user")
  public ResponseEntity<?> readUser(
      @RequestHeader("Authorization") String header,
      @Valid @RequestBody UserDto.Read.Request form) {

    UserDto.Read.Response info = userService.readUser(form);

    return ResponseEntity.ok(info);
  }

  // 회원 UPDATE
  @PutMapping("/user")
  public ResponseEntity<?> updateUser(
      @RequestHeader("Authorization") String header,
      @Valid @RequestBody UserDto.Update.Request form) {

    UserDto.Update.Response updated = userService.updateUser(form);

    return ResponseEntity.ok(updated);
  }

  // 회원 DELETE
  @DeleteMapping("/user")
  public ResponseEntity<?> deleteUser(
      @RequestHeader("Authorization") String header,
      @Valid @RequestBody UserDto.Delete.Request form) {

    userService.deleteUser(form);

    return ResponseEntity.ok("삭제되었습니다.");
  }


}