package kang.tableorder.controller;

import jakarta.validation.Valid;
import kang.tableorder.dto.RestaurantDto;
import kang.tableorder.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

  private final RestaurantService restaurantService;
  private final String AUTH = "Authorization";

  // 매장 CREATE
  @PreAuthorize("hasRole('OWNER')")
  @PostMapping("/restaurant/create")
  public ResponseEntity<Object> createRestaurant(
      @RequestHeader(AUTH) String header,
      @RequestBody RestaurantDto.Create.Request form) {

    RestaurantDto.Create.Response saved = restaurantService.createRestaurant(header, form);

    return ResponseEntity.ok(saved);
  }

  // 매장 READ
  @GetMapping("/restaurant")
  public ResponseEntity<Object> readRestaurant() {

    RestaurantDto.Read.Response info = restaurantService.readRestaurant();

    return ResponseEntity.ok(info);
  }


  // 매장 UPDATE
  @PreAuthorize("hasRole('OWNER')")
  @PutMapping("/restaurant/update")
  public ResponseEntity<Object> updateRestaurant(
      @RequestHeader(AUTH) String header,
      @Valid @RequestBody RestaurantDto.Update.Request form) {

    RestaurantDto.Update.Response updated = restaurantService.updateRestaurant(header, form);

    return ResponseEntity.ok(updated);
  }

  // 매장 DELETE
  @PreAuthorize("hasRole('OWNER')")
  @DeleteMapping("/restaurant/delete")
  public ResponseEntity<Object> deleteRestaurant(
      @RequestHeader(AUTH) String header) {

    boolean deleted = restaurantService.deleteRestaurant(header);

    return ResponseEntity.ok(deleted);
  }
}
