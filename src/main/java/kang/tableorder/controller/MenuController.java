package kang.tableorder.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;
  private final String AUTH = "Authorization";

  // 메뉴 CREATE
  @PreAuthorize("hasRole('OWNER')")
  @PostMapping("/restaurant/menu/create")
  public ResponseEntity<Object> createMenu(
      @RequestHeader(AUTH) String header,
      @Valid @RequestBody MenuDto.Create.Request form) {

    MenuDto.Create.Response saved = menuService.createMenu(header, form);

    return ResponseEntity.ok(saved);
  }

  // 메뉴 리스트 READ
  @GetMapping("/restaurant/menu")
  public ResponseEntity<?> readMenus(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    List<MenuDto.Read.Response> menuList = menuService.readMenus(page, size);

    return ResponseEntity.ok(menuList);
  }

  // 메뉴 READ
  @GetMapping("/restaurant/menu/{menuId}")
  public ResponseEntity<?> readMenu(@PathVariable Integer menuId) {

    MenuDto.Read.Response menu = menuService.readMenu(menuId);

    return ResponseEntity.ok(menu);
  }

  // 메뉴 UPDATE
  @PreAuthorize("hasRole('OWNER')")
  @PutMapping("/restaurant/menu/update/{menuId}")
  public ResponseEntity<Object> updateMenu(
      @RequestHeader(AUTH) String header,
      @PathVariable Integer menuId,
      @Valid @RequestBody MenuDto.Update.Request form) {

    MenuDto.Update.Response updated = menuService.updateMenu(header, menuId, form);

    return ResponseEntity.ok(updated);
  }


  // 메뉴 DELETE
  @PreAuthorize("hasRole('OWNER')")
  @DeleteMapping("/restaurant/menu/delete/{menuId}")
  public ResponseEntity<Object> deleteMenu(
      @RequestHeader(AUTH) String header,
      @PathVariable Integer menuId) {

    boolean deleted = menuService.deleteMenu(header, menuId);

    return ResponseEntity.ok(deleted);
  }
}
