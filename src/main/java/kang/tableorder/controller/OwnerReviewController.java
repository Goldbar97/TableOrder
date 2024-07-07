package kang.tableorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kang.tableorder.dto.OwnerReviewDto;
import kang.tableorder.service.OwnerReviewService;
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

@Tag(name = "점장 리뷰", description = "점장 리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class OwnerReviewController {

  private final OwnerReviewService ownerReviewService;

  // 리뷰 추가
  @Operation(summary = "점장 리뷰 추가", description = "토큰, 매장ID, 메뉴ID, 손님리뷰ID, 리뷰 정보를 받고 리뷰를 추가합니다.")
  @PostMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}/owner-reviews")
  public ResponseEntity<?> createReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @PathVariable Long reviewId,
      @Valid @RequestBody OwnerReviewDto.Create.Request form) {

    OwnerReviewDto.Create.Response saved = ownerReviewService.createReview(restaurantId, menuId,
        reviewId, form);

    return ResponseEntity.ok(saved);
  }

  // 리뷰 리스트 조회
  @Operation(summary = "리뷰 리스트 조회", description = "토큰을 받고 자신의 모든 리뷰를 조회합니다.")
  @GetMapping("/owner/reviews")
  public ResponseEntity<?> readReviewList(
      @RequestHeader("Authorization") String header) {

    List<OwnerReviewDto.Read.Response> info = ownerReviewService.readReviewList();

    return ResponseEntity.ok(info);
  }

  // 리뷰 조회
  @Operation(summary = "리뷰 조회", description = "토큰, 매장ID, 메뉴ID, 손님리뷰ID, 점장리뷰ID 를 받고 자신의 리뷰를 조회합니다.")
  @GetMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}/owner-reviews/{ownerReviewId}")
  public ResponseEntity<?> readReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @PathVariable Long reviewId,
      @PathVariable Long ownerReviewId) {

    OwnerReviewDto.Read.Response info = ownerReviewService.readReview(restaurantId, menuId,
        reviewId, ownerReviewId);

    return ResponseEntity.ok(info);
  }

  // 리뷰 수정
  @Operation(summary = "리뷰 수정", description = "토큰, 매장ID, 메뉴ID, 손님리뷰ID, 점장리뷰ID 를 받고 자신의 리뷰를 수정합니다.")
  @PutMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}/owner-reviews" +
      "/{ownerReviewId}")
  public ResponseEntity<?> updateReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @PathVariable Long reviewId,
      @PathVariable Long ownerReviewId,
      @Valid @RequestBody OwnerReviewDto.Update.Request form) {

    OwnerReviewDto.Update.Response updated = ownerReviewService.updateReview(restaurantId, menuId,
        reviewId, ownerReviewId, form);

    return ResponseEntity.ok(updated);
  }

  // 리뷰 삭제
  @Operation(summary = "리뷰 삭제", description = "토큰, 매장ID, 메뉴ID, 손님리뷰ID, 점장리뷰ID 를 받고 자신의 리뷰를 삭제합니다.")
  @DeleteMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}/owner-reviews/{ownerReviewId}")
  public ResponseEntity<?> deleteReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @PathVariable Long reviewId,
      @PathVariable Long ownerReviewId) {

    ownerReviewService.deleteReview(restaurantId, menuId, reviewId, ownerReviewId);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}