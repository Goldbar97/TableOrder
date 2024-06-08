package kang.tableorder.controller;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

  private final RestaurantService restaurantService;

  // 매장 CREATE
  @PreAuthorize("hasRole('OWNER')")
  @PostMapping("/restaurants")
  public ResponseEntity<?> createRestaurant(
      @RequestHeader("Authorization") String header,
      @Valid @RequestBody RestaurantDto.Create.Request form) {

    RestaurantDto.Create.Response saved = restaurantService.createRestaurant(form);

    return ResponseEntity.ok(saved);
  }

  // 매장 리스트 READ
  @GetMapping("/restaurants")
  public ResponseEntity<?> readRestaurantsList() {

    List<RestaurantDto.Read.Response> info = restaurantService.readRestaurantsList();

    return ResponseEntity.ok(info);
  }

  // 매장 READ
  @GetMapping("/restaurants/{restaurantId}")
  public ResponseEntity<?> readRestaurant(
      @PathVariable Integer restaurantId) {

    RestaurantDto.Read.Response info = restaurantService.readRestaurant(restaurantId);

    return ResponseEntity.ok(info);
  }

  // 매장 UPDATE
  @PreAuthorize("hasRole('OWNER')")
  @PutMapping("/restaurants/{restaurantId}")
  public ResponseEntity<?> updateRestaurant(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer restaurantId,
      @Valid @RequestBody RestaurantDto.Update.Request form) {

    RestaurantDto.Update.Response updated = restaurantService.updateRestaurant(restaurantId, form);

    return ResponseEntity.ok(updated);
  }

  // 매장 DELETE
  @PreAuthorize("hasRole('OWNER')")
  @DeleteMapping("/restaurants/{restaurantId}")
  public ResponseEntity<?> deleteRestaurant(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer restaurantId) {

    restaurantService.deleteRestaurant(restaurantId);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}
