package kang.tableorder.controller;

import jakarta.validation.Valid;
import java.util.List;
import kang.tableorder.dto.OrderDto;
import kang.tableorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants/{restaurantId}/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<?> createOrder(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Integer restaurantId,
      @Valid @RequestBody OrderDto.Create.Request form) {

    OrderDto.Create.Response saved = orderService.createOrder(restaurantId, form);

    return ResponseEntity.ok(saved);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<?> readOrder(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Integer restaurantId,
      @PathVariable Integer orderId,
      @Valid @RequestBody OrderDto.Read.Request form) {

    OrderDto.Read.Response info = orderService.readOrder(restaurantId, orderId, form);

    return ResponseEntity.ok(info);
  }

  @PreAuthorize("hasRole('OWNER')")
  @GetMapping
  public ResponseEntity<?> readOrderList(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer restaurantId) {

    List<OrderDto.Read.Response> info = orderService.readOrderList(restaurantId);

    return ResponseEntity.ok(info);
  }

  @Transactional
  @PreAuthorize("hasRole('OWNER')")
  @PutMapping("/{orderId}")
  public ResponseEntity<?> updateOrder(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer restaurantId,
      @PathVariable Integer orderId,
      @Valid @RequestBody OrderDto.Update.Request form) {

    OrderDto.Update.Response updated = orderService.updateOrder(restaurantId, orderId, form);

    return ResponseEntity.ok(updated);
  }

}
