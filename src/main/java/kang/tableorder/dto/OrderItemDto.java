package kang.tableorder.dto;

import kang.tableorder.entity.OrdersItemEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class OrderItemDto {

  public static class Read {

    public static class Request {

    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String menuName;
      private int count;
      private int totalPrice;

      public static OrderItemDto.Read.Response toDto(OrdersItemEntity ordersItemEntity) {

        return OrderItemDto.Read.Response.builder()
            .id(ordersItemEntity.getId())
            .menuName(ordersItemEntity.getMenuEntity().getName())
            .count(ordersItemEntity.getCount())
            .totalPrice(ordersItemEntity.getTotalPrice())
            .build();
      }
    }
  }
}
