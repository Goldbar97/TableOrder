package kang.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.type.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserDto {

  public static class Code {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "test@naver.com")
      @Email(message = "Email is invalid")
      @NotBlank(message = "Email cannot be blank")
      private String email;
    }
  }

  public static class SignUp {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "test@naver.com")
      @Email(message = "Email is invalid")
      @NotBlank(message = "Email cannot be blank")
      private String email;

      @Schema(defaultValue = "test12")
      @Size(min = 6, max = 6, message = "Verification code must be 6 characters")
      @NotBlank(message = "Verification code cannot be blank")
      private String verificationCode;

      @Schema(defaultValue = "test1234")
      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "Password is invalid")
      private String password;

      @Schema(defaultValue = "testName")
      @NotBlank(message = "Name cannot be blank")
      @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "Name is invalid")
      private String name;

      @Schema(defaultValue = "testNickname")
      @NotBlank(message = "Nickname cannot be blank")
      @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "Invalid characters")
      private String nickname;

      @Schema(defaultValue = "010-1234-5678")
      @NotBlank(message = "PhoneNumber cannot be blank")
      @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "PhoneNumber is invalid")
      private String phoneNumber;

      @Schema(defaultValue = "[\"CUSTOMER\"]")
      @NotNull(message = "Role cannot be null")
      private List<UserRole> role;

      public UserEntity toEntity() {

        return UserEntity.builder()
            .email(this.email)
            .password(this.password)
            .name(this.name)
            .nickname(this.nickname)
            .phoneNumber(this.phoneNumber)
            .role(this.role)
            .build();
      }
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String email;
      private String name;
      private String nickname;
      private String phoneNumber;
      private List<UserRole> role;
      private LocalDateTime createdAt;

      public static UserDto.SignUp.Response toDto(UserEntity userEntity) {

        return Response.builder()
            .id(userEntity.getId())
            .email(userEntity.getEmail())
            .name(userEntity.getName())
            .nickname(userEntity.getNickname())
            .phoneNumber(userEntity.getPhoneNumber())
            .role(userEntity.getRole())
            .createdAt(userEntity.getCreatedAt())
            .build();
      }
    }
  }

  public static class SignIn {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "test@naver.com")
      @Email(message = "Email is invalid")
      @NotBlank(message = "Email cannot be blank")
      private String email;

      @Schema(defaultValue = "test1234")
      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
      private String password;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String email;
      private List<UserRole> role;
      private String token;

      public static UserDto.SignIn.Response toDto(UserEntity userEntity, String token) {

        return UserDto.SignIn.Response.builder()
            .id(userEntity.getId())
            .email(userEntity.getEmail())
            .role(userEntity.getRole())
            .token(token)
            .build();
      }
    }
  }

  public static class Read {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "test1234")
      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
      private String password;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String email;
      private String name;
      private String nickname;
      private String phoneNumber;
      private List<UserRole> role;
      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;

      public static UserDto.Read.Response toDto(UserEntity userEntity) {

        return UserDto.Read.Response.builder()
            .id(userEntity.getId())
            .email(userEntity.getEmail())
            .name(userEntity.getName())
            .nickname(userEntity.getNickname())
            .phoneNumber(userEntity.getPhoneNumber())
            .role(userEntity.getRole())
            .createdAt(userEntity.getCreatedAt())
            .updatedAt(userEntity.getUpdatedAt())
            .build();
      }
    }
  }

  public static class Update {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "test1234")
      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
      private String password;

      @Schema(defaultValue = "test5678")
      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
      private String newPassword;

      @Schema(defaultValue = "testNewNickname")
      @NotBlank(message = "Nickname cannot be blank")
      @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$",
          message = "Nickname is invalid")
      private String newNickname;

      @Schema(defaultValue = "010-5678-1234")
      @NotBlank(message = "PhoneNumber cannot be blank")
      @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
          message = "PhoneNumber is invalid")
      private String newPhoneNumber;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String email;
      private String name;
      private String nickname;
      private String phoneNumber;
      private List<UserRole> role;
      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;

      public static UserDto.Update.Response toDto(UserEntity userEntity) {

        return UserDto.Update.Response.builder()
            .id(userEntity.getId())
            .email(userEntity.getEmail())
            .name(userEntity.getName())
            .nickname(userEntity.getNickname())
            .phoneNumber(userEntity.getPhoneNumber())
            .role(userEntity.getRole())
            .createdAt(userEntity.getCreatedAt())
            .updatedAt(userEntity.getUpdatedAt())
            .build();
      }
    }
  }

  public static class Delete {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "test5678")
      @NotBlank(message = "Password cannot be blank")
      @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$",
          message = "Password is invalid")
      private String password;
    }

    public static class Response {

    }
  }
}