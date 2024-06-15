package kang.tableorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "주문", description = "주문 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants/{restaurantId}/orders")
public class OrderController {

  private final OrderService orderService;

  // 주문 추가
  @Operation(summary = "주문 추가", description = "토큰(비필수), 매장ID, 주문 정보를 받고 주문을 추가합니다.")
  @PostMapping
  public ResponseEntity<?> createOrder(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Long restaurantId,
      @Valid @RequestBody OrderDto.Create.Request form) {

    OrderDto.Create.Response saved = orderService.createOrder(restaurantId, form);

    return ResponseEntity.ok(saved);
  }

  // 주문 조회
  @Operation(summary = "주문 조회", description = "토큰(비필수), 매장ID, 주문ID, 주문 정보를 받고 주문을 조회합니다.")
  @GetMapping("/{orderId}")
  public ResponseEntity<?> readOrder(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Long restaurantId,
      @PathVariable Long orderId,
      @Valid @RequestBody OrderDto.Read.Request form) {

    OrderDto.Read.Response info = orderService.readOrder(restaurantId, orderId, form);

    return ResponseEntity.ok(info);
  }

  // 주문 리스트 조회
  @Operation(summary = "주문 리스트 조회", description = "토큰, 매장ID 를 받고 주문 리스트를 조회합니다.")
  @PreAuthorize("hasRole('OWNER')")
  @GetMapping
  public ResponseEntity<?> readOrderList(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId) {

    List<OrderDto.Read.Response> info = orderService.readOrderList(restaurantId);

    return ResponseEntity.ok(info);
  }

  // 주문 수정
  @Operation(summary = "주문 수정", description = "토큰, 매장ID, 주문ID, 주문 정보를 받고 주문을 수정합니다.")
  @Transactional
  @PreAuthorize("hasRole('OWNER')")
  @PutMapping("/{orderId}")
  public ResponseEntity<?> updateOrder(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long orderId,
      @Valid @RequestBody OrderDto.Update.Request form) {

    OrderDto.Update.Response updated = orderService.updateOrder(restaurantId, orderId, form);

    return ResponseEntity.ok(updated);
  }

}
