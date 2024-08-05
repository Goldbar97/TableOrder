package kang.tableorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kang.tableorder.dto.MenuDto;
import kang.tableorder.service.MenuService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메뉴", description = "메뉴 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants/{restaurantId}/menu")
public class MenuController {

  private final MenuService menuService;

  // 메뉴 CREATE
  @Operation(summary = "메뉴 추가", description = "토큰, 매장ID, 메뉴 정보를 받고 메뉴를 추가합니다.")
  @PreAuthorize("hasRole('OWNER')")
  @PostMapping
  public ResponseEntity<?> createMenu(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @Valid @RequestBody MenuDto.Create.Request request) {

    MenuDto.Create.Response saved = menuService.createMenu(restaurantId, request);

    return ResponseEntity.ok(saved);
  }

  // 메뉴 리스트 READ
  @Operation(summary = "메뉴 리스트 조회", description = "매장ID 를 받고 메뉴 리스트를 조회합니다. 가장 최근의 리뷰 한 개를 불러옵니다.")
  @GetMapping
  public ResponseEntity<?> readMenuList(
      @PathVariable Long restaurantId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    List<MenuDto.Read.Response> menuList = menuService.readMenuList(restaurantId, page, size);

    return ResponseEntity.ok(menuList);
  }

  // 메뉴 READ
  @Operation(summary = "메뉴 조회", description = "매장ID, 메뉴ID를 받고 메뉴를 조회합니다. 메뉴의 모든 리뷰를 불러옵니다.")
  @GetMapping("/{menuId}")
  public ResponseEntity<?> readMenu(
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    MenuDto.Read.Response menu = menuService.readMenu(restaurantId, menuId, page, size);

    return ResponseEntity.ok(menu);
  }

  // 메뉴 UPDATE
  @Operation(summary = "메뉴 수정", description = "토큰, 매장ID, 메뉴ID, 메뉴 정보를 받고 메뉴를 수정합니다.")
  @PreAuthorize("hasRole('OWNER')")
  @PutMapping("/{menuId}")
  public ResponseEntity<?> updateMenu(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @Valid @RequestBody MenuDto.Update.Request request) {

    MenuDto.Update.Response updated = menuService.updateMenu(restaurantId, menuId, request);

    return ResponseEntity.ok(updated);
  }

  // 메뉴 DELETE
  @Operation(summary = "메뉴 삭제", description = "토큰, 매장ID, 메뉴ID 를 받고 메뉴를 삭제합니다.")
  @PreAuthorize("hasRole('OWNER')")
  @DeleteMapping("/{menuId}")
  public ResponseEntity<?> deleteMenu(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId) {

    menuService.deleteMenu(restaurantId, menuId);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}