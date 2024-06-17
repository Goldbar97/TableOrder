package kang.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import kang.tableorder.entity.AccountEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class AccountDto {

  public static class Create {

    public static class Request {

    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private int balance;

      public static AccountDto.Create.Response toDto(AccountEntity accountEntity) {

        return AccountDto.Create.Response.builder()
            .id(accountEntity.getId())
            .balance(accountEntity.getBalance())
            .build();
      }
    }
  }

  public static class Read {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "testTabletMacId")
      @NotBlank(message = "TabletMacId cannot be blank")
      private String tabletMacId;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String userName;
      private int balance;
      private LocalDateTime createdAt;

      public static AccountDto.Read.Response toDto(AccountEntity accountEntity, String userName) {

        return AccountDto.Read.Response.builder()
            .id(accountEntity.getId())
            .userName(userName)
            .balance(accountEntity.getBalance())
            .createdAt(accountEntity.getCreatedAt())
            .build();
      }
    }
  }

  public static class UserDeposit {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "30000")
      @Min(value = 0, message = "Amount minimum is 0")
      private int amount;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String userName;
      private int balance;

      public static AccountDto.UserDeposit.Response toDto(AccountEntity accountEntity,
          String userName) {

        return AccountDto.UserDeposit.Response.builder()
            .id(accountEntity.getId())
            .userName(userName)
            .balance(accountEntity.getBalance())
            .build();
      }
    }
  }

  public static class GuestDeposit {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "30000")
      @Min(value = 0, message = "Amount minimum is 0")
      private int amount;

      @Schema(defaultValue = "testTabletMacId")
      @NotBlank(message = "TabletMacId cannot be blank")
      private String tabletMacId;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String userName;
      private int balance;

      public static AccountDto.GuestDeposit.Response toDto(AccountEntity accountEntity,
          String userName) {

        return AccountDto.GuestDeposit.Response.builder()
            .id(accountEntity.getId())
            .userName(userName)
            .balance(accountEntity.getBalance())
            .build();
      }
    }
  }
}
