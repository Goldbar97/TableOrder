package kang.tableorder.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import kang.tableorder.entity.CartItemEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CartDto {

  public static class Create {

    @Getter
    @Setter
    public static class Request {

      @Min(value = 1, message = "Count minimum is 1")
      private int count;

      @NotBlank(message = "TabletMacId cannot be blank")
      private String tabletMacId;
    }

    public static class Response {

    }
  }

  public static class Read {

    @Getter
    @Setter
    public static class Request {

      @NotBlank(message = "TabletMacId cannot be blank")
      private String tabletMacId;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      List<CartItemDto.Read.Response> cartItems;
      int totalPrice;
    }
  }

  public static class Update {

    @Getter
    @Setter
    public static class Request {

      @Min(value = 1, message = "Count minimum is 1")
      private int count;

      @NotBlank(message = "TabletMacId cannot be blank")
      private String tabletMacId;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String menuName;
      private int menuPrice;
      private int count;
      private int totalPrice;

      public static CartDto.Update.Response toDto(CartItemEntity cartItemEntity) {

        return CartDto.Update.Response.builder()
            .id(cartItemEntity.getId())
            .menuName(cartItemEntity.getMenuEntity().getName())
            .menuPrice(cartItemEntity.getMenuEntity().getPrice())
            .count(cartItemEntity.getCount())
            .totalPrice(cartItemEntity.getTotalPrice())
            .build();
      }
    }
  }

  public static class Delete {

    @Getter
    @Setter
    public static class Request {

      @NotBlank(message = "TabletMacId cannot be blank")
      private String tabletMacId;
    }

    public static class Response {

    }
  }
}
