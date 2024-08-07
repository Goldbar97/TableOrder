package kang.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CustomerReviewDto {

  public static class Create {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "testDescription")
      @NotBlank(message = "Description cannot be blank")
      private String description;

      @Schema(defaultValue = "3")
      @Min(value = 1, message = "Rating minimum is 1")
      @Max(value = 5, message = "Rating maximum is 5")
      private int rating;

      public CustomerReviewEntity toEntity(MenuEntity menuEntity, UserEntity userEntity) {

        return CustomerReviewEntity.builder()
            .menuEntity(menuEntity)
            .userEntity(userEntity)
            .description(description)
            .rating(rating)
            .build();
      }
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private Long restaurantId;
      private Long menuId;
      private String menuName;
      private String userNickname;
      private int rating;
      private int visitedCount;
      private LocalDateTime createdAt;

      public static CustomerReviewDto.Create.Response toDto(
          CustomerReviewEntity customerReviewEntity, int visitedCount) {

        return Response.builder()
            .id(customerReviewEntity.getId())
            .restaurantId(
                customerReviewEntity.getMenuEntity().getRestaurantEntity().getId())
            .menuId(customerReviewEntity.getMenuEntity().getId())
            .menuName(customerReviewEntity.getMenuEntity().getName())
            .userNickname(customerReviewEntity.getUserEntity().getNickname())
            .rating(customerReviewEntity.getRating())
            .visitedCount(visitedCount)
            .createdAt(customerReviewEntity.getCreatedAt())
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
      private String menuName;
      private String userNickname;
      private String description;
      private int rating;
      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;

      public static CustomerReviewDto.Read.Response toDto(
          CustomerReviewEntity customerReviewEntity) {

        return CustomerReviewDto.Read.Response.builder()
            .id(customerReviewEntity.getId())
            .restaurantId(
                customerReviewEntity.getMenuEntity().getRestaurantEntity().getId())
            .menuId(customerReviewEntity.getMenuEntity().getId())
            .menuName(customerReviewEntity.getMenuEntity().getName())
            .userNickname(customerReviewEntity.getUserEntity().getNickname())
            .description(customerReviewEntity.getDescription())
            .rating(customerReviewEntity.getRating())
            .createdAt(customerReviewEntity.getCreatedAt())
            .updatedAt(customerReviewEntity.getUpdatedAt())
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

      @Schema(defaultValue = "5")
      @Min(value = 1, message = "Rating minimum is 1")
      @Max(value = 5, message = "Rating maximum is 5")
      private int rating;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String menuName;
      private String userNickname;
      private int rating;
      private LocalDateTime updatedAt;

      public static CustomerReviewDto.Update.Response toDto(
          CustomerReviewEntity customerReviewEntity) {

        return CustomerReviewDto.Update.Response.builder()
            .id(customerReviewEntity.getId())
            .menuName(customerReviewEntity.getMenuEntity().getName())
            .userNickname(customerReviewEntity.getUserEntity().getNickname())
            .rating(customerReviewEntity.getRating())
            .updatedAt(customerReviewEntity.getUpdatedAt())
            .build();
      }
    }
  }
}