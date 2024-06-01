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
  private final String AUTH = "Authorization";

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
      @RequestHeader(AUTH) String header,
      @RequestBody String password) {

    UserDto.Read.Response info = userService.readUser(header, password);

    return ResponseEntity.ok(info);
  }

  // 회원 UPDATE
  @PutMapping("/user/update")
  public ResponseEntity<?> updateUser(
      @RequestHeader(AUTH) String header,
      @Valid @RequestBody UserDto.Update.Request form) {

    UserDto.Update.Response updated = userService.updateUser(header, form);

    return ResponseEntity.ok(updated);
  }

  // 회원 DELETE
  @DeleteMapping("/user/delete")
  public ResponseEntity<?> deleteUser(
      @RequestHeader(AUTH) String header,
      @RequestBody String password) {

    boolean deleted = userService.deleteUser(header, password);

    return ResponseEntity.ok(deleted);
  }


}