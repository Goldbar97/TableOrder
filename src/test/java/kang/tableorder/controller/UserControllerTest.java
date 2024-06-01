package kang.tableorder.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kang.tableorder.dto.UserDto;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.service.UserService;
import kang.tableorder.type.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

  final UserDto.SignUp.Request signUpRequest = UserDto.SignUp.Request.builder()
      .email("test@naver.com")
      .password("test1234")
      .name("test")
      .nickname("test")
      .phoneNumber("010-1234-1234")
      .role(new ArrayList<>(Arrays.asList(UserRole.CUSTOMER)))
      .build();
  final UserDto.SignIn.Request signInRequest = UserDto.SignIn.Request.builder()
      .email("test@naver.com")
      .password("test1234")
      .build();
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserService userService;

  @Test
  @DisplayName("SignUpSuccess")
  void signUpSuccess() throws Exception {
    // given
    // when
    // then
    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(signUpRequest.getEmail()))
        .andExpect(jsonPath("$.name").value(signUpRequest.getName()))
        .andExpect(jsonPath("$.nickname").value(signUpRequest.getNickname()))
        .andExpect(jsonPath("$.role[0]").value(signUpRequest.getRole().get(0).toString()));
  }

  @Test
  @DisplayName("SignUpFailEmailExists")
  void signUpFailEmailExists() throws Exception {
    // given
    userService.signUp(signUpRequest);

    // when
    // then
    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest)))
        .andExpect(status().is(ErrorCode.ALREADY_EXISTS_EMAIL.getStatus().value()))
        .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXISTS_EMAIL.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXISTS_EMAIL.getValue()));
  }

  @Test
  @DisplayName("SignUpFailNicknameExists")
  void signUpFailNicknameExists() throws Exception {
    // given
    UserDto.SignUp.Request prior = UserDto.SignUp.Request.builder()
        .email("test@google.com")
        .password("test1234")
        .name("test")
        .nickname("test")
        .phoneNumber("010-1234-1234")
        .role(new ArrayList<>(List.of(UserRole.CUSTOMER)))
        .build();

    userService.signUp(prior);

    // when
    // then
    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest)))
        .andExpect(status().is(ErrorCode.ALREADY_EXISTS_NICKNAME.getStatus().value()))
        .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXISTS_NICKNAME.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXISTS_NICKNAME.getValue()));
  }

  @Test
  @DisplayName("SignInSuccess")
  void signInSuccess() throws Exception {
    // given
    userService.signUp(signUpRequest);

    // when
    // then
    mockMvc.perform(post("/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signInRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(signInRequest.getEmail()))
        .andExpect(jsonPath("$.role[0]").value(signUpRequest.getRole().get(0).toString()));
  }

  @Test
  @DisplayName("SignInFailEmailNotExists")
  void signInFailEmailNotExists() throws Exception {
    // given
    // when
    // then
    mockMvc.perform(post("/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signInRequest)))
        .andExpect(status().is(ErrorCode.NO_USER.getStatus().value()))
        .andExpect(jsonPath("$.code").value(ErrorCode.NO_USER.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.NO_USER.getValue()));
  }

  @Test
  @DisplayName("SignInFailWrongPassword")
  void signInFailWrongPassword() throws Exception {
    // given
    userService.signUp(signUpRequest);

    UserDto.SignIn.Request signInRequest = UserDto.SignIn.Request.builder()
        .email("test@naver.com")
        .password("test5678")
        .build();

    // when
    // then
    mockMvc.perform(post("/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signInRequest)))
        .andExpect(status().is(ErrorCode.WRONG_PASSWORD.getStatus().value()))
        .andExpect(jsonPath("$.code").value(ErrorCode.WRONG_PASSWORD.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.WRONG_PASSWORD.getValue()));
  }

  @Test
  @DisplayName("ReadSuccess")
  void readSuccess() throws Exception {
    // given
    userService.signUp(signUpRequest);

    UserDto.SignIn.Request signInRequest = UserDto.SignIn.Request.builder()
        .email("test@naver.com")
        .password("test1234")
        .build();

    UserDto.SignIn.Response response = userService.signIn(signInRequest);

    // when
    // then
    mockMvc.perform(get("/user")
            .header("Authorization", String.format("Bearer %s", response.getToken()))
            .content("test1234"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(signUpRequest.getEmail()))
        .andExpect(jsonPath("$.name").value(signUpRequest.getName()))
        .andExpect(jsonPath("$.nickname").value(signUpRequest.getNickname()))
        .andExpect(jsonPath("$.role[0]").value(signUpRequest.getRole().get(0).toString()));
  }

  @Test
  @DisplayName("ReadFailWrongPassword")
  void readFailWrongPassword() throws Exception {
    // given
    userService.signUp(signUpRequest);

    UserDto.SignIn.Request signInRequest = UserDto.SignIn.Request.builder()
        .email("test@naver.com")
        .password("test1234")
        .build();

    UserDto.SignIn.Response response = userService.signIn(signInRequest);

    mockMvc.perform(get("/user")
            .header("Authorization", String.format("Bearer %s", response.getToken()))
            .content("test5678"))
        .andExpect(status().is(ErrorCode.WRONG_PASSWORD.getStatus().value()))
        .andExpect(jsonPath("$.code").value(ErrorCode.WRONG_PASSWORD.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.WRONG_PASSWORD.getValue()));
  }


  @Test
  @DisplayName("UpdateSuccess")
  void updateSuccess() throws Exception {
    // given
    userService.signUp(signUpRequest);

    UserDto.SignIn.Response response = userService.signIn(signInRequest);

    UserDto.Update.Request updateRequest = UserDto.Update.Request.builder()
        .password("test1234")
        .newPassword("test5678")
        .newNickname("testNewNickname")
        .newPhoneNumber("010-1234-5678")
        .build();

    // when
    // then
    mockMvc.perform(put("/user/update")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", response.getToken()))
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(signUpRequest.getEmail()))
        .andExpect(jsonPath("$.name").value(signUpRequest.getName()))
        .andExpect(jsonPath("$.nickname").value(updateRequest.getNewNickname()))
        .andExpect(jsonPath("$.phoneNumber").value(updateRequest.getNewPhoneNumber()))
        .andExpect(jsonPath("$.role[0]").value(signUpRequest.getRole().get(0).toString()));
  }

  @Test
  @DisplayName("UpdateSuccessAndSignInSuccessWithNewPassword")
  void updateSuccessAndSignInSuccessWithNewPassword() throws Exception {
    // given
    userService.signUp(signUpRequest);

    UserDto.SignIn.Response response = userService.signIn(signInRequest);

    UserDto.Update.Request updateRequest = UserDto.Update.Request.builder()
        .password("test1234")
        .newPassword("test5678")
        .newNickname("testNewNickname")
        .newPhoneNumber("010-1234-5678")
        .build();

    userService.updateUser(response.getToken(), updateRequest);

    signInRequest.setPassword(updateRequest.getNewPassword());

    // when
    // then
    mockMvc.perform(post("/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signInRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(signInRequest.getEmail()))
        .andExpect(jsonPath("$.role[0]").value(signUpRequest.getRole().get(0).toString()));
  }

  @Test
  @DisplayName("UpdateFailWrongPassword")
  void updateFailWrongPassword() throws Exception {
    // given
    userService.signUp(signUpRequest);

    UserDto.SignIn.Response response = userService.signIn(signInRequest);

    UserDto.Update.Request updateRequest = UserDto.Update.Request.builder()
        .password("test5678")
        .newPassword("test5678")
        .newNickname("testNewNickname")
        .newPhoneNumber("010-1234-5678")
        .build();

    // when
    // then
    mockMvc.perform(put("/user/update")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", response.getToken()))
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().is(ErrorCode.WRONG_PASSWORD.getStatus().value()))
        .andExpect(jsonPath("$.code").value(ErrorCode.WRONG_PASSWORD.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.WRONG_PASSWORD.getValue()));
  }

  @Test
  @DisplayName("DeleteSuccess")
  void deleteSuccess() throws Exception {
    // given
    userService.signUp(signUpRequest);
    UserDto.SignIn.Response response = userService.signIn(signInRequest);

    // when
    // then
    mockMvc.perform(delete("/user/delete")
            .header("Authorization", String.format("Bearer %s", response.getToken()))
            .content("test1234")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(true));
  }

  @Test
  @DisplayName("DeleteFailWrongPassword")
  void deleteFailWrongPassword() throws Exception {
    // given
    userService.signUp(signUpRequest);
    UserDto.SignIn.Response response = userService.signIn(signInRequest);

    // when
    // then
    mockMvc.perform(delete("/user/delete")
            .header("Authorization", String.format("Bearer %s", response.getToken()))
            .content("test5678")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(ErrorCode.WRONG_PASSWORD.getStatus().value()))
        .andExpect(jsonPath("$.code").value(ErrorCode.WRONG_PASSWORD.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.WRONG_PASSWORD.getValue()));
  }

}