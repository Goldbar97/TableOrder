package kang.tableorder.dto;

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

      private String tabletMacId;
    }

    public static class Response {

    }
  }

  public static class Read {

    @Getter
    @Setter
    public static class Request {

      private String tabletMacId;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      List<CartItemDto.Read.Response> cartItems;
      Integer totalPrice;
    }
  }

  public static class Update {

    @Getter
    @Setter
    public static class Request {

      private Integer count;
      private String tabletMacId;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Integer id;
      private String menuName;
      private Integer menuPrice;
      private Integer count;
      private Integer totalPrice;

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

      private String tabletMacId;
    }

    public static class Response {

    }
  }
}
