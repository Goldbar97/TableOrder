package kang.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.OwnerReviewEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class OwnerReviewDto {

  public static class Create {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "testDescription")
      @NotBlank(message = "Description cannot be blank")
      private String description;

      public OwnerReviewEntity toEntity(RestaurantEntity restaurantEntity, MenuEntity menuEntity,
          CustomerReviewEntity customerReviewEntity, UserEntity userEntity) {

        return OwnerReviewEntity.builder()
            .restaurantEntity(restaurantEntity)
            .menuEntity(menuEntity)
            .customerReviewEntity(customerReviewEntity)
            .userEntity(userEntity)
            .description(this.description)
            .build();
      }
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private LocalDateTime createdAt;

      public static OwnerReviewDto.Create.Response toDto(OwnerReviewEntity ownerReviewEntity) {

        return OwnerReviewDto.Create.Response.builder()
            .id(ownerReviewEntity.getId())
            .createdAt(ownerReviewEntity.getCreatedAt())
            .build();
      }
    }
  }

  public static class Read {

    public static class Request {

    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private Long restaurantId;
      private Long menuId;
      private Long customerReviewId;
      private String userNickname;
      private String description;
      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;

      public static OwnerReviewDto.Read.Response toDto(OwnerReviewEntity ownerReviewEntity) {

        return Response.builder()
            .id(ownerReviewEntity.getId())
            .restaurantId(ownerReviewEntity.getId())
            .menuId(ownerReviewEntity.getMenuEntity().getId())
            .customerReviewId(ownerReviewEntity.getCustomerReviewEntity().getId())
            .userNickname(ownerReviewEntity.getUserEntity().getNickname())
            .description(ownerReviewEntity.getDescription())
            .createdAt(ownerReviewEntity.getCreatedAt())
            .updatedAt(ownerReviewEntity.getUpdatedAt())
            .build();
      }
    }
  }

  public static class Update {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "testNewDescription")
      @NotBlank(message = "Description cannot be blank")
      private String description;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private Long restaurantId;
      private Long menuId;
      private Long customerReviewId;
      private String userNickname;
      private String description;
      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;

      public static OwnerReviewDto.Update.Response toDto(OwnerReviewEntity ownerReviewEntity) {

        return Update.Response.builder()
            .id(ownerReviewEntity.getId())
            .restaurantId(ownerReviewEntity.getId())
            .menuId(ownerReviewEntity.getMenuEntity().getId())
            .customerReviewId(ownerReviewEntity.getCustomerReviewEntity().getId())
            .userNickname(ownerReviewEntity.getUserEntity().getNickname())
            .description(ownerReviewEntity.getDescription())
            .createdAt(ownerReviewEntity.getCreatedAt())
            .updatedAt(ownerReviewEntity.getUpdatedAt())
            .build();
      }
    }
  }
}
