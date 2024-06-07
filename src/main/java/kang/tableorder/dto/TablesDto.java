package kang.tableorder.dto;

import kang.tableorder.entity.CartEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.TablesEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class TablesDto {

  public static class Create {

    @Builder
    @Getter
    @Setter
    public static class Request {

      private Integer number;
      private String tabletMacId;

      public TablesEntity toEntity(RestaurantEntity restaurantEntity) {

        return TablesEntity.builder()
            .restaurantEntity(restaurantEntity)
            .cartEntity(new CartEntity())
            .number(this.number)
            .tabletMacId(this.tabletMacId)
            .build();
      }
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Integer id;
      private Integer number;
      private String tabletMacId;

      public static TablesDto.Create.Response toDto(TablesEntity tablesEntity) {

        return TablesDto.Create.Response.builder()
            .id(tablesEntity.getId())
            .number(tablesEntity.getNumber())
            .tabletMacId(tablesEntity.getTabletMacId())
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
      private Integer number;
      private String tabletMacId;

      public static TablesDto.Read.Response toDto(TablesEntity tablesEntity) {

        return TablesDto.Read.Response.builder()
            .id(tablesEntity.getId())
            .number(tablesEntity.getNumber())
            .tabletMacId(tablesEntity.getTabletMacId())
            .build();
      }
    }
  }

  public static class Update {

    @Builder
    @Getter
    @Setter
    public static class Request {

      private Integer number;
      private String tabletMacId;
    }

    @Builder
    @Getter
    @Setter
    public static class Response {

      private Integer id;
      private Integer number;
      private String tabletMacId;

      public static TablesDto.Update.Response toDto(TablesEntity tablesEntity) {

        return TablesDto.Update.Response.builder()
            .id(tablesEntity.getId())
            .number(tablesEntity.getNumber())
            .tabletMacId(tablesEntity.getTabletMacId())
            .build();
      }
    }
  }
}
