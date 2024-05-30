package kang.tableorder.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.type.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserDto {

  public static class SignUp {

    @Getter
    @Setter
    public static class Request {

      @Email(message = "Email is invalid")
      @NotBlank(message = "Email cannot be blank")
      private String email;

      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
      private String password;

      @NotBlank(message = "Name cannot be blank")
      @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "Name is invalid")
      private String name;

      @NotBlank(message = "Nickname cannot be blank")
      @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$",
          message = "Invalid characters")
      private String nickname;

      @NotBlank(message = "PhoneNumber cannot be blank")
      @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
          message = "PhoneNumber is invalid")
      private String phoneNumber;

      @NotNull(message = "Role cannot be null")
      private UserRole role;

      public static UserEntity toEntity(UserDto.SignUp.Request form) {

        return UserEntity.builder()
            .email(form.getEmail())
            .password(form.getPassword())
            .name(form.getName())
            .nickname(form.getNickname())
            .phoneNumber(form.getPhoneNumber())
            .roles(List.of((form.getRole())))
            .build();
      }
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

      public static UserDto.SignUp.Response toDto(UserEntity userEntity) {
        return Response.builder()
            .id(userEntity.getId())
            .email(userEntity.getEmail())
            .name(userEntity.getName())
            .nickname(userEntity.getNickname())
            .role(userEntity.getRoles().toString())
            .build();
      }
    }


  }

  public static class SignIn {

    @Getter
    @Setter
    public static class Request {

      @Email(message = "Email is invalid")
      @NotBlank(message = "Email cannot be blank")
      private String email;

      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
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

      public static UserDto.SignIn.Response toDto(UserEntity userEntity, String token) {
        return UserDto.SignIn.Response.builder()
            .id(userEntity.getId())
            .email(userEntity.getEmail())
            .roles(userEntity.getRoles())
            .token(token)
            .build();
      }
    }
  }

  public static class Read {

    @Getter
    @Setter
    public static class Request {

      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
      private String password;
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

      public static UserDto.Read.Response toDto(UserEntity userEntity) {
        return UserDto.Read.Response.builder()
            .id(userEntity.getId())
            .email(userEntity.getEmail())
            .name(userEntity.getName())
            .nickname(userEntity.getNickname())
            .role(userEntity.getRoles().toString())
            .build();
      }
    }
  }

  public static class Update {

    @Getter
    @Setter
    public static class Request {

      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
      private String password;

      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
      private String newPassword;

      @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$",
          message = "Nickname is invalid")
      private String newNickname;

      @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
          message = "PhoneNumber is invalid")
      private String newPhoneNumber;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Integer id;
      private String email;
      private String name;
      private String nickname;
      private String phoneNumber;
      private List<UserRole> roles;
      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;

      public static UserDto.Update.Response toDto(UserEntity userEntity) {
        return UserDto.Update.Response.builder()
            .id(userEntity.getId())
            .email(userEntity.getEmail())
            .name(userEntity.getName())
            .nickname(userEntity.getNickname())
            .phoneNumber(userEntity.getPhoneNumber())
            .roles(userEntity.getRoles())
            .createdAt(userEntity.getCreatedAt())
            .updatedAt(userEntity.getUpdatedAt())
            .build();
      }
    }
  }
}
