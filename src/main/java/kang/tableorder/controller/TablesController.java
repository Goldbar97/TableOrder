package kang.tableorder.controller;

import jakarta.validation.Valid;
import java.util.List;
import kang.tableorder.dto.TablesDto;
import kang.tableorder.service.TablesService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('OWNER')")
@RequestMapping("/restaurants/{restaurantId}/tables")
@RequiredArgsConstructor
public class TablesController {

  private final TablesService tablesService;

  // 테이블
  @Transactional
  @PostMapping
  public ResponseEntity<?> createTables(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @Valid @RequestBody TablesDto.Create.Request form) {

    TablesDto.Create.Response saved = tablesService.createTables(restaurantId, form);

    return ResponseEntity.ok(saved);
  }

  // 테이블 리스트 읽기
  @GetMapping
  public ResponseEntity<?> readTablesList(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId) {

    List<TablesDto.Read.Response> tablesList = tablesService.readTablesList(restaurantId);

    return ResponseEntity.ok(tablesList);
  }

  // 테이블 읽기
  @GetMapping("/{tablesId}")
  public ResponseEntity<?> readTables(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long tablesId) {

    TablesDto.Read.Response tables = tablesService.readTables(restaurantId, tablesId);

    return ResponseEntity.ok(tables);
  }

  // 테이블 수정
  @Transactional
  @PutMapping("/{tablesId}")
  public ResponseEntity<?> updateTables(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long tablesId,
      @Valid @RequestBody TablesDto.Update.Request form) {

    TablesDto.Update.Response updated = tablesService.updateTables(restaurantId, tablesId, form);

    return ResponseEntity.ok(updated);
  }

  // 테이블 삭제
  @Transactional
  @DeleteMapping("/{tablesId}")
  public ResponseEntity<?> deleteTables(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long tablesId) {

    tablesService.deleteTables(restaurantId, tablesId);

    return ResponseEntity.ok("삭제되었습니다.");
  }

  // 테이블 리스트 삭제
  @Transactional
  @DeleteMapping
  public ResponseEntity<?> deleteTablesList(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId) {

    tablesService.deleteTablesList(restaurantId);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}
