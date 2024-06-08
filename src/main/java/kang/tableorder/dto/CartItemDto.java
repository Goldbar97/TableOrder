package kang.tableorder.dto;

import kang.tableorder.entity.CartItemEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CartItemDto {

  public static class Read {

    public static class Request {

    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Integer id;
      private String menuName;
      private int menuPrice;
      private int count;
      private int totalPrice;

      public static CartItemDto.Read.Response toDto(CartItemEntity cartItemEntity) {

        return CartItemDto.Read.Response.builder()
            .id(cartItemEntity.getId())
            .menuName(cartItemEntity.getMenuEntity().getName())
            .menuPrice(cartItemEntity.getMenuEntity().getPrice())
            .count(cartItemEntity.getCount())
            .totalPrice(cartItemEntity.getTotalPrice())
            .build();
      }
    }
  }
}
