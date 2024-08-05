package kang.tableorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kang.tableorder.dto.CustomerReviewDto;
import kang.tableorder.service.CustomerReviewService;
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

@Tag(name = "손님 리뷰", description = "손님 리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerReviewController {

  private final CustomerReviewService customerReviewService;

  // 리뷰 추가
  @Operation(summary = "손님 리뷰 추가", description = "토큰, 매장ID, 메뉴ID, 리뷰 정보를 받고 리뷰를 추가합니다.")
  @PostMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews")
  public ResponseEntity<?> createReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @Valid @RequestBody CustomerReviewDto.Create.Request request) {

    CustomerReviewDto.Create.Response saved = customerReviewService.createReview(restaurantId,
        menuId, request);

    return ResponseEntity.ok(saved);
  }

  // 리뷰 리스트 조회
  @Operation(summary = "리뷰 리스트 조회", description = "토큰을 받고 자신의 모든 리뷰를 조회합니다.")
  @GetMapping("/customer/reviews")
  public ResponseEntity<?> readReviewList(
      @RequestHeader("Authorization") String header) {

    List<CustomerReviewDto.Read.Response> info = customerReviewService.readReviewList();

    return ResponseEntity.ok(info);
  }

  // 리뷰 조회
  @Operation(summary = "리뷰 조회", description = "토큰, 매장ID, 메뉴ID, 리뷰ID 를 받고 리뷰를 조회합니다.")
  @GetMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}")
  public ResponseEntity<?> readReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @PathVariable Long reviewId) {

    CustomerReviewDto.Read.Response info = customerReviewService.readReview(restaurantId,
        menuId,
        reviewId);

    return ResponseEntity.ok(info);
  }

  // 리뷰 수정
  @Operation(summary = "리뷰 수정", description = "토큰, 매장ID, 메뉴ID, 리뷰ID, 리뷰 정보를 받고 리뷰를 수정합니다.")
  @PutMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}")
  public ResponseEntity<?> updateReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @PathVariable Long reviewId,
      @Valid @RequestBody CustomerReviewDto.Update.Request request) {

    CustomerReviewDto.Update.Response updated = customerReviewService.updateReview(restaurantId,
        menuId,
        reviewId,
        request);

    return ResponseEntity.ok(updated);
  }

  // 리뷰 삭제
  @Operation(summary = "리뷰 삭제", description = "토큰, 매장ID, 메뉴ID, 리뷰ID 를 받고 리뷰를 삭제합니다.")
  @DeleteMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}")
  public ResponseEntity<?> deleteReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @PathVariable Long reviewId) {

    customerReviewService.deleteReview(restaurantId, menuId, reviewId);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}