package kang.tableorder.controller;

import kang.tableorder.dto.SignInDto;
import kang.tableorder.dto.SignUpDto;
import kang.tableorder.security.TokenProvider;
import kang.tableorder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final TokenProvider tokenProvider;

  @PostMapping("/signup")
  public ResponseEntity<Object> signUp(
      @RequestBody SignUpDto.Request form) {
    SignUpDto.Response signedUp = userService.signUp(form);

    return ResponseEntity.ok(signedUp);
  }

  @PostMapping("/signin")
  public ResponseEntity<Object> signIn(
      @RequestBody SignInDto.Request form) {
    SignInDto.Response signedIn = userService.signIn(form);

    String token = tokenProvider.generateToken(signedIn.getEmail(), signedIn.getRoles());
    signedIn.setToken(token);

    return ResponseEntity.ok(signedIn);
  }
}