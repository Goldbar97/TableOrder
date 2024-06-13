package kang.tableorder.controller;

import jakarta.validation.Valid;
import java.util.List;
import kang.tableorder.dto.MenuDto;
import kang.tableorder.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants/{restaurantId}/menu")
public class MenuController {

  private final MenuService menuService;

  // 메뉴 CREATE
  @Transactional
  @PreAuthorize("hasRole('OWNER')")
  @PostMapping
  public ResponseEntity<?> createMenu(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer restaurantId,
      @Valid @RequestBody MenuDto.Create.Request form) {

    MenuDto.Create.Response saved = menuService.createMenu(restaurantId, form);

    return ResponseEntity.ok(saved);
  }

  // 메뉴 리스트 READ
  @GetMapping
  public ResponseEntity<?> readMenuList(
      @PathVariable Integer restaurantId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    List<MenuDto.Read.Response> menuList = menuService.readMenuList(restaurantId, page, size);

    return ResponseEntity.ok(menuList);
  }

  // 메뉴 READ
  @GetMapping("/{menuId}")
  public ResponseEntity<?> readMenu(
      @PathVariable Integer restaurantId,
      @PathVariable Integer menuId) {

    MenuDto.Read.Response menu = menuService.readMenu(restaurantId, menuId);

    return ResponseEntity.ok(menu);
  }

  // 메뉴 UPDATE
  @Transactional
  @PreAuthorize("hasRole('OWNER')")
  @PutMapping("/{menuId}")
  public ResponseEntity<?> updateMenu(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer restaurantId,
      @PathVariable Integer menuId,
      @Valid @RequestBody MenuDto.Update.Request form) {

    MenuDto.Update.Response updated = menuService.updateMenu(restaurantId, menuId, form);

    return ResponseEntity.ok(updated);
  }


  // 메뉴 DELETE
  @Transactional
  @PreAuthorize("hasRole('OWNER')")
  @DeleteMapping("/{menuId}")
  public ResponseEntity<?> deleteMenu(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer restaurantId,
      @PathVariable Integer menuId) {

    menuService.deleteMenu(restaurantId, menuId);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}
