package kang.tableorder.controller;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TablesController {

  private final TablesService tablesService;
  private final String AUTH = "Authorization";

  @PreAuthorize("hasRole('OWNER')")
  @PostMapping("/restaurant/tables/create")
  public ResponseEntity<?> createTables(
      @RequestHeader(AUTH) String header,
      @RequestBody TablesDto.Create.Request form) {

    TablesDto.Create.Response saved = tablesService.createTables(header, form);

    return ResponseEntity.ok(saved);
  }

  @PreAuthorize("hasRole('OWNER')")
  @GetMapping("/restaurant/tables")
  public ResponseEntity<?> readTables(
      @RequestHeader(AUTH) String header) {

    List<TablesDto.Read.Response> tablesList = tablesService.readTables(header);

    return ResponseEntity.ok(tablesList);
  }

  @PreAuthorize("hasRole('OWNER')")
  @PutMapping("/restaurant/tables/{tablesId}")
  public ResponseEntity<?> updateTables(
      @RequestHeader(AUTH) String header,
      @PathVariable Integer tablesId,
      @RequestBody TablesDto.Update.Request form) {

    TablesDto.Update.Response updated = tablesService.updateTables(header, tablesId, form);

    return ResponseEntity.ok(updated);
  }

  @PreAuthorize("hasRole('OWNER')")
  @DeleteMapping("/restaurant/tables/{tablesId}")
  public ResponseEntity<?> deleteTables(
      @RequestHeader(AUTH) String header,
      @PathVariable Integer tablesId) {

    boolean deleted = tablesService.deleteTables(header, tablesId);

    return ResponseEntity.ok(deleted);
  }
}
