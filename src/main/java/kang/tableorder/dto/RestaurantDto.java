package kang.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class RestaurantDto {

  public static class Create {

    @Builder
    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "testName")
      @NotBlank(message = "Name cannot be blank")
      private String name;

      @Schema(defaultValue = "testLocation")
      @NotBlank(message = "Location cannot be blank")
      private String location;

      @Schema(defaultValue = "testDescription")
      @NotBlank(message = "Description cannot be blank")
      private String description;

      @Schema(defaultValue = "010-1234-5678")
      @NotBlank(message = "PhoneNumber cannot be blank")
      @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
          message = "PhoneNumber is invalid")
      private String phoneNumber;

      public RestaurantEntity toEntity(UserEntity userEntity) {

        return RestaurantEntity.builder()
            .userEntity(userEntity)
            .name(this.name)
            .location(this.location)
            .description(this.description)
            .phoneNumber(this.phoneNumber)
            .build();
      }
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String name;
      private String location;
      private String description;
      private String phoneNumber;

      public static RestaurantDto.Create.Response toDto(RestaurantEntity restaurantEntity) {

        return RestaurantDto.Create.Response.builder()
            .id(restaurantEntity.getId())
            .name(restaurantEntity.getName())
            .location(restaurantEntity.getLocation())
            .description(restaurantEntity.getDescription())
            .phoneNumber(restaurantEntity.getPhoneNumber())
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
      private Long userId;
      private String userName;
      private String userPhoneNumber;
      private String name;
      private String location;
      private String description;
      private String phoneNumber;

      public static RestaurantDto.Read.Response toDto(RestaurantEntity restaurantEntity) {

        return RestaurantDto.Read.Response.builder()
            .id(restaurantEntity.getId())
            .userId(restaurantEntity.getUserEntity().getId())
            .userName(restaurantEntity.getUserEntity().getName())
            .userPhoneNumber(restaurantEntity.getUserEntity().getPhoneNumber())
            .name(restaurantEntity.getName())
            .location(restaurantEntity.getLocation())
            .description(restaurantEntity.getDescription())
            .phoneNumber(restaurantEntity.getPhoneNumber())
            .build();
      }
    }
  }

  public static class Update {

    @Getter
    @Setter
    public static class Request {

      @Schema(defaultValue = "testNewName")
      @NotBlank(message = "Name cannot be blank")
      private String name;

      @Schema(defaultValue = "testNewLocation")
      @NotBlank(message = "Location cannot be blank")
      private String location;

      @Schema(defaultValue = "testNewDescription")
      @NotBlank(message = "Description cannot be blank")
      private String description;

      @Schema(defaultValue = "010-5678-1234")
      @NotBlank(message = "PhoneNumber cannot be blank")
      @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
          message = "PhoneNumber is invalid")
      private String phoneNumber;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Long id;
      private String name;
      private String location;
      private String description;
      private String phoneNumber;

      public static RestaurantDto.Update.Response toDto(RestaurantEntity restaurantEntity) {

        return RestaurantDto.Update.Response.builder()
            .id(restaurantEntity.getId())
            .name(restaurantEntity.getName())
            .location(restaurantEntity.getLocation())
            .description(restaurantEntity.getDescription())
            .phoneNumber(restaurantEntity.getPhoneNumber())
            .build();
      }
    }
  }
}