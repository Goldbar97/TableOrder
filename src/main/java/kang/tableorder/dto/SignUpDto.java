package kang.tableorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SignUpDto {

  @Getter
  @Setter
  public static class Request {

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String role;
  }

  @Builder
  @Getter
  @Setter
  public static class Response {

    private Integer id;
    private String email;
    private String name;
    private String nickname;
    private String role;
  }
}
