package kang.tableorder.dto;

import java.util.List;
import kang.tableorder.type.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SignInDto {

  @Getter
  @Setter
  public static class Request {

    private String email;
    private String password;
  }

  @Builder
  @Getter
  @Setter
  public static class Response {

    private Integer id;
    private String email;
    private List<UserRole> roles;
    private String token;
  }
}
