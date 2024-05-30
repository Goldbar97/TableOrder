package kang.tableorder.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import kang.tableorder.dto.SignInDto;
import kang.tableorder.dto.SignUpDto;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.security.TokenProvider;
import kang.tableorder.service.UserService;
import kang.tableorder.type.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @MockBean
  private UserService userService;

  @MockBean
  private TokenProvider tokenProvider;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("SignUpSuccess")
  void signUpSuccess() throws Exception {
    List<UserRole> rolesList = new ArrayList<>();
    rolesList.add(UserRole.CUSTOMER);

    // given
    SignUpDto.Response response = SignUpDto.Response.builder()
        .id(1)
        .email("test@naver.com")
        .name("kang")
        .nickname("sjk")
        .role(rolesList.toString())
        .build();

    SignUpDto.Request request = new SignUpDto.Request();

    given(userService.signUp(any(SignUpDto.Request.class))).willReturn(response);

    // when
    // then
    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.email").value("test@naver.com"))
        .andExpect(jsonPath("$.name").value("kang"))
        .andExpect(jsonPath("$.nickname").value("sjk"))
        .andExpect(jsonPath("$.role").value(rolesList.toString()));
  }

  @Test
  @DisplayName("SignUpFailEmailExists")
  void signUpFailEmailExists() throws Exception {
    // given
    SignUpDto.Request request = new SignUpDto.Request();

    given(userService.signUp(any(SignUpDto.Request.class))).willThrow(new CustomException(
        ErrorCode.ALREADY_EXISTS_EMAIL));

    // when
    // then
    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXISTS_EMAIL.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXISTS_EMAIL.getValue()));
  }

  @Test
  @DisplayName("SignUpFailNicknameExists")
  void signUpFailNicknameExists() throws Exception {
    // given
    SignUpDto.Request request = new SignUpDto.Request();

    given(userService.signUp(any(SignUpDto.Request.class))).willThrow(new CustomException(
        ErrorCode.ALREADY_EXISTS_NICKNAME));

    // when
    // then
    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXISTS_NICKNAME.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXISTS_NICKNAME.getValue()));
  }

  @Test
  @DisplayName("SignInSuccess")
  void signInSuccess() throws Exception {
    // given
    List<UserRole> rolesList = new ArrayList<>();
    rolesList.add(UserRole.CUSTOMER);
    rolesList.add(UserRole.OWNER);

    SignInDto.Request request = new SignInDto.Request();

    SignInDto.Response response = SignInDto.Response.builder()
        .id(1)
        .email("test@naver.com")
        .roles(rolesList)
        .build();

    given(userService.signIn(any(SignInDto.Request.class))).willReturn(response);
    given(tokenProvider.generateToken(any(String.class), any())).willReturn("tokenForTest");
    // when
    // then
    mockMvc.perform(post("/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.email").value("test@naver.com"))
        .andExpect(jsonPath("$.roles", containsInAnyOrder("CUSTOMER", "OWNER")))
        .andExpect(jsonPath("$.token").value("tokenForTest"));
  }

  @Test
  @DisplayName("SignInFailEmailNotExists")
  void signInFailEmailNotExists() throws Exception {
    // given
    SignUpDto.Request request = new SignUpDto.Request();

    given(userService.signIn(any(SignInDto.Request.class))).willThrow(new CustomException(
        ErrorCode.NO_USER));

    // when
    // then
    mockMvc.perform(post("/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(ErrorCode.NO_USER.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.NO_USER.getValue()));
  }

  @Test
  @DisplayName("SignInFailWrongPassword")
  void signInFailWrongPassword() throws Exception {
    // given
    SignUpDto.Request request = new SignUpDto.Request();

    given(userService.signIn(any(SignInDto.Request.class))).willThrow(new CustomException(
        ErrorCode.WRONG_PASSWORD));

    // when
    // then
    mockMvc.perform(post("/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(ErrorCode.WRONG_PASSWORD.getStatus().value()))
        .andExpect(jsonPath("$.message").value(ErrorCode.WRONG_PASSWORD.getValue()));
  }
}