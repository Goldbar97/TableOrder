package kang.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import kang.tableorder.entity.OrdersEntity;
import kang.tableorder.entity.OrdersItemEntity;
import kang.tableorder.type.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class OrderDto {

  public static class Create {

    @Getter
    @Setter
    public static class Request {

      @NotNull(message = "RestaurantId cannot be null")
      private Long restaurantId;

      @Schema(defaultValue = "testTabletMacId")
      @NotBlank(message = "TabletMacId cannot be blank")
      private String tabletMacId;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String restaurantName;
      private int tablesNumber;
      private String userNickname;
      private int totalPrice;
      private LocalDateTime createdAt;
      private OrderStatus status;

      public static OrderDto.Create.Response toDto(OrdersEntity ordersEntity) {

        return Response.builder()
            .id(ordersEntity.getId())
            .restaurantName(ordersEntity.getRestaurantEntity().getName())
            .tablesNumber(ordersEntity.getTablesEntity().getNumber())
            .userNickname(
                ordersEntity.getUserEntity() != null ? ordersEntity.getUserEntity()
                    .getNickname()
                    : "비회원")
            .totalPrice(ordersEntity.getTotalPrice())
            .createdAt(ordersEntity.getCreatedAt())
            .status(ordersEntity.getStatus())
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
      private List<OrderItemDto.Read.Response> orderItems;
      private int tablesNumber;
      private String userNickname;
      private int totalPrice;
      private LocalDateTime createdAt;
      private OrderStatus status;

      public static OrderDto.Read.Response toDto(OrdersEntity ordersEntity,
          List<OrdersItemEntity> ordersItemEntities) {

        return OrderDto.Read.Response.builder()
            .id(ordersEntity.getId())
            .orderItems(
                ordersItemEntities.stream()
                    .map(OrderItemDto.Read.Response::toDto)
                    .toList())
            .tablesNumber(ordersEntity.getTablesEntity().getNumber())
            .userNickname(
                ordersEntity.getUserEntity() != null ? ordersEntity.getUserEntity()
                    .getNickname()
                    : "비회원")
            .totalPrice(ordersEntity.getTotalPrice())
            .createdAt(ordersEntity.getCreatedAt())
            .status(ordersEntity.getStatus())
            .build();
      }
    }
  }

  public static class Update {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "ACCEPTED")
      @NotNull(message = "Status cannot be null")
      private OrderStatus status;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private List<OrderItemDto.Read.Response> orderItems;
      private int tablesNumber;
      private String userNickname;
      private int totalPrice;
      private LocalDateTime createdAt;
      private OrderStatus status;

      public static OrderDto.Update.Response toDto(OrdersEntity ordersEntity,
          List<OrdersItemEntity> ordersItemEntities) {

        return OrderDto.Update.Response.builder()
            .id(ordersEntity.getId())
            .orderItems(
                ordersItemEntities.stream()
                    .map(OrderItemDto.Read.Response::toDto)
                    .toList())
            .tablesNumber(ordersEntity.getTablesEntity().getNumber())
            .userNickname(
                ordersEntity.getUserEntity() != null ? ordersEntity.getUserEntity()
                    .getNickname()
                    : "비회원")
            .totalPrice(ordersEntity.getTotalPrice())
            .createdAt(ordersEntity.getCreatedAt())
            .status(ordersEntity.getStatus())
            .build();
      }
    }
  }

  public static class Payment {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "testTabletMacId")
      @NotBlank(message = "TabletMacId cannot be blank")
      private String tabletMacId;
    }

    public static class Response {

    }
  }
}