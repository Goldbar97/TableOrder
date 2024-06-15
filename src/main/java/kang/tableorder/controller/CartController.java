package kang.tableorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kang.tableorder.dto.CartDto;
import kang.tableorder.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카트", description = "카트 관련 API")
@RestController
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  // 카트에 메뉴 추가
  @Operation(summary = "장바구니 담기", description = "토큰(비필수), 매장ID, 메뉴ID, 카트 정보를 받고 회원/비회원 장바구니에 메뉴를 담습니다.")
  @Transactional
  @PostMapping("/restaurants/{restaurantId}/menu/{menuId}")
  public ResponseEntity<?> addMenuToCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @Valid @RequestBody CartDto.Create.Request form) {

    cartService.addMenuToCart(restaurantId, menuId, form);

    return ResponseEntity.ok("추가됐습니다.");
  }

  // 카트 아이템 리스트 조회
  @Operation(summary = "장바구니 조회", description = "토큰(비필수), 카트 정보를 받고 회원/비회원 장바구니를 확인합니다.")
  @GetMapping("/cart")
  public ResponseEntity<?> readMenuListInCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @Valid @RequestBody CartDto.Read.Request form) {

    CartDto.Read.Response cartItemsList = cartService.readMenuListInCart(form);

    return ResponseEntity.ok(cartItemsList);
  }

  // 카트 아이템 수정
  @Operation(summary = "장바구니 요소 수정", description = "토큰(비필수), 아이템ID, 카트 정보를 받고 장바구니에 담긴 메뉴를 수정합니다.")
  @PutMapping("/cart/{cartItemId}")
  public ResponseEntity<?> updateMenuInCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Long cartItemId,
      @Valid @RequestBody CartDto.Update.Request form) {

    CartDto.Update.Response updated = cartService.updateMenuInCart(cartItemId, form);

    return ResponseEntity.ok(updated);
  }

  // 카트 아이템 삭제
  @Operation(summary = "장바구니 요소 삭제", description = "토큰(비필수), 아이템ID, 카트 정보를 받고 장바구니에 담긴 메뉴를 삭제합니다.")
  @Transactional
  @DeleteMapping("/cart/{cartItemId}")
  public ResponseEntity<?> deleteMenuInCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @PathVariable Long cartItemId,
      @Valid @RequestBody CartDto.Delete.Request form) {

    cartService.deleteMenuInCart(cartItemId, form);

    return ResponseEntity.ok("삭제되었습니다.");
  }

  // 카트 비우기
  @Operation(summary = "장바구니 비우기", description = "토큰(비필수), 카트 정보를 받고 장바구니를 비웁니다.")
  @Transactional
  @DeleteMapping("/cart")
  public ResponseEntity<?> deleteAllMenuInCart(
      @RequestHeader(value = "Authorization", required = false) String header,
      @Valid @RequestBody CartDto.Delete.Request form) {

    cartService.deleteAllMenuInCart(form);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}