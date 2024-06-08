package kang.tableorder.controller;

import jakarta.validation.Valid;
import kang.tableorder.dto.CartDto;
import kang.tableorder.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class CartController {

  private final CartService cartService;

  // 카트에 메뉴 추가
  @PostMapping("/restaurants/{restaurantId}/menu/{menuId}/cart")
  public ResponseEntity<?> addMenuToCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Integer restaurantId,
      @PathVariable Integer menuId,
      @Valid @RequestBody CartDto.Create.Request form) {

    cartService.addMenuToCart(restaurantId, menuId, form);

    return ResponseEntity.ok("추가됐습니다.");
  }

  // 카트 아이템 리스트 조회
  @GetMapping("/cart")
  public ResponseEntity<?> readMenuListInCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @Valid @RequestBody CartDto.Read.Request form) {

    CartDto.Read.Response cartItemsList = cartService.readMenuListInCart(form);

    return ResponseEntity.ok(cartItemsList);
  }

  // 카트 아이템 수정
  @PutMapping("/cart/{cartItemId}")
  public ResponseEntity<?> updateMenuInCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Integer cartItemId,
      @Valid @RequestBody CartDto.Update.Request form) {

    CartDto.Update.Response updated = cartService.updateMenuInCart(cartItemId, form);

    return ResponseEntity.ok(updated);
  }

  // 카트 아이템 삭제
  @DeleteMapping("/cart/{cartItemId}")
  public ResponseEntity<?> deleteMenuInCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Integer cartItemId,
      @Valid @RequestBody CartDto.Delete.Request form) {

    cartService.deleteMenuInCart(cartItemId, form);

    return ResponseEntity.ok("삭제되었습니다.");
  }

  // 카트 비우기
  @DeleteMapping("/cart")
  public ResponseEntity<?> deleteAllMenuInCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @Valid @RequestBody CartDto.Delete.Request form) {

    cartService.deleteAllMenuInCart(form);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}