package kang.tableorder.setup;

import java.util.ArrayList;
import java.util.Arrays;
import kang.tableorder.dto.RestaurantDto;
import kang.tableorder.dto.TablesDto;
import kang.tableorder.dto.UserDto;
import kang.tableorder.type.UserRole;

public class TablesControllerSetup {

  public final String email = "test@naver.com";

  public final UserDto.SignUp.Request signUpRequest = UserDto.SignUp.Request.builder()
      .email("test@naver.com")
      .password("test1234")
      .name("test")
      .nickname("test")
      .phoneNumber("010-1234-5678")
      .role(new ArrayList<>(Arrays.asList(UserRole.OWNER)))
      .build();

  public final UserDto.SignIn.Request signInRequest = UserDto.SignIn.Request.builder()
      .email("test@naver.com")
      .password("test1234")
      .build();

  public final RestaurantDto.Create.Request createRestaurantRequest = RestaurantDto.Create.Request.builder()
      .name("testRestaurant")
      .location("testLocation")
      .description("testDescription")
      .phoneNumber("010-1234-5678")
      .build();

  public final TablesDto.Create.Request createTablesRequest = TablesDto.Create.Request.builder()
      .number(1)
      .tabletMacId("testTabletMacId")
      .build();

  public final TablesDto.Update.Request updateTablesRequest = TablesDto.Update.Request.builder()
      .number(2)
      .tabletMacId("newTestTabletMacId")
      .build();
}
