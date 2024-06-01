package kang.tableorder.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kang.tableorder.entity.MenuEntity;
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

      private Integer id;
      private MenuCategory category;
      private String name;
      private String imageUrl;
      private Integer price;
      private String description;
      private Integer spiciness;
      private Boolean isAvailable;

      public static MenuDto.Read.Response toDto(MenuEntity menuEntity) {
        return MenuDto.Read.Response.builder()
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

  public static class Update {

    @Getter
    @Setter
    public static class Request {

      private MenuCategory category;

      @Pattern(regexp = "^[a-zA-Z0-9가-힣 ]*$",
          message = "Invalid characters")
      private String name;

      private String imageUrl;

      @Min(value = 1, message = "Price must be higher than 0")
      private Integer price;

      private String description;

      @Min(value = 0, message = "Minimum spiciness is 0")
      @Max(value = 4, message = "Maximum spiciness is 4")
      private Integer spiciness;

      private Boolean isAvailable;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Integer id;
      private MenuCategory category;
      private String name;
      private String imageUrl;
      private Integer price;
      private String description;
      private Integer spiciness;
      private Boolean isAvailable;

      public static MenuDto.Create.Response toDto(MenuEntity menuEntity) {
        return Create.Response.builder()
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

      @NotNull(message = "Category cannot be null")
      private MenuCategory category;

      @NotBlank(message = "Name cannot be blank")
      @Pattern(regexp = "^[a-zA-Z0-9가-힣 ]*$",
          message = "Invalid characters")
      private String name;

      @NotBlank(message = "Image cannot be blank")
      private String imageUrl;

      @NotNull(message = "Price cannot be blank")
      @Min(value = 1, message = "Price must be higher than 0")
      private Integer price;

      @NotBlank(message = "Description cannot be blank")
      private String description;

      @NotNull(message = "Spiciness cannot be blank")
      @Min(value = 0, message = "Minimum spiciness is 0")
      @Max(value = 4, message = "Maximum spiciness is 4")
      private Integer spiciness;

      @NotNull(message = "IsAvailable cannot be blank")
      private Boolean isAvailable;

      public static MenuEntity toEntity(MenuDto.Create.Request form) {
        return MenuEntity.builder()
            .category(form.getCategory())
            .name(form.getName())
            .imageUrl(form.getImageUrl())
            .price(form.getPrice())
            .description(form.getDescription())
            .spiciness(form.getSpiciness())
            .isAvailable(form.getIsAvailable())
            .build();
      }
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Integer id;
      private MenuCategory category;
      private String name;
      private String imageUrl;
      private Integer price;
      private String description;
      private Integer spiciness;
      private Boolean isAvailable;

      public static MenuDto.Create.Response toDto(MenuEntity menuEntity) {
        return Response.builder()
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