package kang.tableorder.setup;

import java.util.ArrayList;
import java.util.Arrays;
import kang.tableorder.dto.UserDto;
import kang.tableorder.type.UserRole;

public class UserControllerSetup {

  public final String email = "test@naver.com";

  public final UserDto.SignUp.Request signUpRequest = UserDto.SignUp.Request.builder()
      .email("test@naver.com")
      .password("test1234")
      .name("test")
      .nickname("test")
      .phoneNumber("010-1234-5678")
      .role(new ArrayList<>(Arrays.asList(UserRole.CUSTOMER)))
      .build();

  public final UserDto.SignIn.Request signInRequest = UserDto.SignIn.Request.builder()
      .email("test@naver.com")
      .password("test1234")
      .build();
}
