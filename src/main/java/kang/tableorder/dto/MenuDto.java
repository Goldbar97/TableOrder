package kang.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Collections;
import java.util.List;
import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.type.MenuCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MenuDto {

  public static class Read {

    public static class Request {

    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private MenuCategory category;
      private String name;
      private String imageUrl;
      private int price;
      private String description;
      private int spiciness;
      private Boolean isAvailable;
      private List<CustomerReviewDto.Read.Response> customerReview;

      public static MenuDto.Read.Response toDto(
          MenuEntity menuEntity,
          CustomerReviewEntity customerReviewEntity) {

        MenuDto.Read.Response response = MenuDto.Read.Response.builder()
            .id(menuEntity.getId())
            .category(menuEntity.getCategory())
            .name(menuEntity.getName())
            .imageUrl(menuEntity.getImageUrl())
            .price(menuEntity.getPrice())
            .description(menuEntity.getDescription())
            .spiciness(menuEntity.getSpiciness())
            .isAvailable(menuEntity.getIsAvailable())
            .build();

        if (customerReviewEntity == null) {
          response.setCustomerReview(Collections.emptyList());
        } else {
          response.setCustomerReview(
              List.of(CustomerReviewDto.Read.Response.toDto(customerReviewEntity)));
        }

        return response;
      }

      public static MenuDto.Read.Response toDto(
          MenuEntity menuEntity,
          List<CustomerReviewDto.Read.Response> list) {

        return MenuDto.Read.Response.builder()
            .id(menuEntity.getId())
            .category(menuEntity.getCategory())
            .name(menuEntity.getName())
            .imageUrl(menuEntity.getImageUrl())
            .price(menuEntity.getPrice())
            .description(menuEntity.getDescription())
            .spiciness(menuEntity.getSpiciness())
            .isAvailable(menuEntity.getIsAvailable())
            .customerReview(list)
            .build();
      }
    }
  }

  public static class Update {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "TYPE1")
      private MenuCategory category;

      @Schema(defaultValue = "testNewName")
      @NotBlank(message = "Name cannot be blank")
      @Pattern(regexp = "^[a-zA-Z0-9가-힣 ]*$",
          message = "Invalid characters")
      private String name;

      @Schema(defaultValue = "testNewImageUrl")
      @NotBlank(message = "ImageUrl cannot be blank")
      private String imageUrl;

      @Schema(defaultValue = "20000")
      @Min(value = 0, message = "Price minimum is 0")
      private int price;

      @Schema(defaultValue = "testNewDescription")
      @NotBlank(message = "Description cannot be blank")
      private String description;

      @Schema(defaultValue = "4")
      @Min(value = 0, message = "Spiciness minimum is 0")
      @Max(value = 4, message = "Spiciness maximum is 4")
      private int spiciness;

      @Schema(defaultValue = "true")
      private Boolean isAvailable;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private MenuCategory category;
      private String name;
      private String imageUrl;
      private int price;
      private String description;
      private int spiciness;
      private Boolean isAvailable;

      public static MenuDto.Update.Response toDto(MenuEntity menuEntity) {

        return MenuDto.Update.Response.builder()
            .id(menuEntity.getId())
            .category(menuEntity.getCategory())
            .name(menuEntity.getName())
            .imageUrl(menuEntity.getImageUrl())
            .price(menuEntity.getPrice())
            .description(menuEntity.getDescription())
            .spiciness(menuEntity.getSpiciness())
            .isAvailable(menuEntity.getIsAvailable())
            .build();
      }
    }
  }

  public static class Create {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "TYPE1")
      @NotNull(message = "Category cannot be null")
      private MenuCategory category;

      @Schema(defaultValue = "testName")
      @NotBlank(message = "Name cannot be blank")
      @Pattern(regexp = "^[a-zA-Z0-9가-힣 ]*$",
          message = "Invalid characters")
      private String name;

      @Schema(defaultValue = "testImageUrl")
      @NotBlank(message = "ImageUrl cannot be blank")
      private String imageUrl;

      @Schema(defaultValue = "10000")
      @Min(value = 0, message = "Price minimum is 0")
      private int price;

      @Schema(defaultValue = "testDescription")
      @NotBlank(message = "Description cannot be blank")
      private String description;

      @Schema(defaultValue = "0")
      @Min(value = 0, message = "Spiciness minimum is 0")
      @Max(value = 4, message = "Spiciness maximum is 4")
      private int spiciness;

      @Schema(defaultValue = "true")
      @NotNull(message = "IsAvailable cannot be null")
      private Boolean isAvailable;

      public MenuEntity toEntity(RestaurantEntity restaurantEntity) {

        return MenuEntity.builder()
            .restaurantEntity(restaurantEntity)
            .category(this.category)
            .name(this.name)
            .imageUrl(this.imageUrl)
            .price(this.price)
            .description(this.description)
            .spiciness(this.spiciness)
            .isAvailable(this.isAvailable)
            .build();
      }
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private MenuCategory category;
      private String name;
      private String imageUrl;
      private int price;
      private String description;
      private int spiciness;
      private Boolean isAvailable;

      public static MenuDto.Create.Response toDto(MenuEntity menuEntity) {

        return MenuDto.Create.Response.builder()
            .id(menuEntity.getId())
            .category(menuEntity.getCategory())
            .name(menuEntity.getName())
            .imageUrl(menuEntity.getImageUrl())
            .price(menuEntity.getPrice())
            .description(menuEntity.getDescription())
            .spiciness(menuEntity.getSpiciness())
            .isAvailable(menuEntity.getIsAvailable())
            .build();
      }
    }
  }
}