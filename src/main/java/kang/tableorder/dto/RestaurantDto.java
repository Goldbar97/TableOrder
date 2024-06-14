package kang.tableorder.dto;

import jakarta.validation.constraints.NotBlank;
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

      @NotBlank(message = "Name cannot be blank")
      private String name;

      @NotBlank(message = "Location cannot be blank")
      private String location;

      @NotBlank(message = "Description cannot be blank")
      private String description;

      @NotBlank(message = "PhoneNumber cannot be blank")
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

      private Integer id;
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

      private Integer id;
      private Integer userId;
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

      @NotBlank(message = "Name cannot be blank")
      private String name;

      @NotBlank(message = "Location cannot be blank")
      private String location;

      @NotBlank(message = "Description cannot be blank")
      private String description;

      @NotBlank(message = "PhoneNumber cannot be blank")
      private String phoneNumber;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Integer id;
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