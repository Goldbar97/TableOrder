package kang.tableorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kang.tableorder.dto.TablesDto;
import kang.tableorder.service.TablesService;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테이블", description = "테이블 관련 API")
@RestController
@PreAuthorize("hasRole('OWNER')")
@RequestMapping("/restaurants/{restaurantId}/tables")
@RequiredArgsConstructor
public class TablesController {

  private final TablesService tablesService;

  // 테이블
  @Operation(summary = "테이블 추가", description = "토큰, 매장ID, 테이블 정보를 받고 테이블을 추가합니다.")
  @PostMapping
  public ResponseEntity<?> createTables(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @Valid @RequestBody TablesDto.Create.Request form) {

    TablesDto.Create.Response saved = tablesService.createTables(restaurantId, form);

    return ResponseEntity.ok(saved);
  }


  // 테이블 리스트 읽기
  @Operation(summary = "테이블 리스트 조회", description = "토큰, 매장ID 를 받고 해당 매장의 테이블을 조회합니다.")
  @GetMapping
  public ResponseEntity<?> readTablesList(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId) {

    List<TablesDto.Read.Response> tablesList = tablesService.readTablesList(restaurantId);

    return ResponseEntity.ok(tablesList);
  }

  // 테이블 읽기
  @Operation(summary = "테이블 정보 조회", description = "토큰, 매장ID, 테이블ID 를 받고 해당 테이블을 조회합니다.")
  @GetMapping("/{tablesId}")
  public ResponseEntity<?> readTables(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long tablesId) {

    TablesDto.Read.Response tables = tablesService.readTables(restaurantId, tablesId);

    return ResponseEntity.ok(tables);
  }

  // 테이블 수정
  @Operation(summary = "테이블 수정", description = "토큰, 매장ID, 테이블ID 를 받고 해당 테이블을 수정합니다.")
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
  @Operation(summary = "테이블 삭제", description = "토큰, 매장ID, 테이블ID 를 받고 해당 테이블을 삭제합니다.")
  @DeleteMapping("/{tablesId}")
  public ResponseEntity<?> deleteTables(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long tablesId) {

    tablesService.deleteTables(restaurantId, tablesId);

    return ResponseEntity.ok("삭제되었습니다.");
  }

  // 테이블 리스트 삭제
  @Operation(summary = "테이블 전부 삭제", description = "토큰, 매장ID 를 받고 전체 테이블을 삭제합니다.")
  @DeleteMapping
  public ResponseEntity<?> deleteTablesList(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId) {

    tablesService.deleteTablesList(restaurantId);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}
