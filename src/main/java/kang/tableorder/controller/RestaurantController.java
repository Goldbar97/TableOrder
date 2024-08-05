package kang.tableorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kang.tableorder.dto.RestaurantDto;
import kang.tableorder.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "매장", description = "매장 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

  private final RestaurantService restaurantService;

  // 매장 CREATE
  @Operation(summary = "매장 추가", description = "토큰, 매장 정보를 받고 매장을 추가합니다.")
  @PreAuthorize("hasRole('OWNER')")
  @PostMapping
  public ResponseEntity<?> createRestaurant(
      @RequestHeader("Authorization") String header,
      @Valid @RequestBody RestaurantDto.Create.Request request) {

    RestaurantDto.Create.Response saved = restaurantService.createRestaurant(request);

    return ResponseEntity.ok(saved);
  }

  // 매장 리스트 READ
  @Operation(summary = "매장 리스트 조회", description = "전체 매장을 조회합니다.")
  @GetMapping
  public ResponseEntity<?> readRestaurantList() {

    List<RestaurantDto.Read.Response> info = restaurantService.readRestaurantList();

    return ResponseEntity.ok(info);
  }

  // 매장 READ
  @Operation(summary = "매장 조회", description = "매장ID 를 받고 해당 매장을 조회합니다.")
  @GetMapping("/{restaurantId}")
  public ResponseEntity<?> readRestaurant(
      @PathVariable Long restaurantId) {

    RestaurantDto.Read.Response info = restaurantService.readRestaurant(restaurantId);

    return ResponseEntity.ok(info);
  }

  // 매장 UPDATE
  @Operation(summary = "매장 수정", description = "토큰, 매장ID, 매장 정보를 받고 매장을 수정합니다.")
  @PreAuthorize("hasRole('OWNER')")
  @PutMapping("/{restaurantId}")
  public ResponseEntity<?> updateRestaurant(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @Valid @RequestBody RestaurantDto.Update.Request request) {

    RestaurantDto.Update.Response updated = restaurantService.updateRestaurant(
        restaurantId, request);

    return ResponseEntity.ok(updated);
  }

  // 매장 DELETE
  @Operation(summary = "매장 삭제", description = "토큰, 매장ID 를 받고 해당 매장을 삭제합니다.")
  @PreAuthorize("hasRole('OWNER')")
  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<?> deleteRestaurant(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId) {

    restaurantService.deleteRestaurant(restaurantId);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}