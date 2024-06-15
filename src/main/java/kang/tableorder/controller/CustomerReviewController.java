package kang.tableorder.controller;

import jakarta.validation.Valid;
import java.util.List;
import kang.tableorder.dto.CustomerReviewDto;
import kang.tableorder.service.CustomerReviewService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerReviewController {

  private final CustomerReviewService customerReviewService;

  // 리뷰 추가
  @Transactional
  @PostMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews")
  public ResponseEntity<?> createReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @Valid @RequestBody CustomerReviewDto.Create.Request form) {

    CustomerReviewDto.Create.Response saved = customerReviewService.createReview(restaurantId,
        menuId, form);

    return ResponseEntity.ok(saved);
  }

  // 리뷰 리스트 조회
  @GetMapping("/customer/reviews")
  public ResponseEntity<?> readReviewList(
      @RequestHeader("Authorization") String header) {

    List<CustomerReviewDto.Read.Response> info = customerReviewService.readReviewList();

    return ResponseEntity.ok(info);
  }

  // 리뷰 조회
  @GetMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}")
  public ResponseEntity<?> readReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @PathVariable Long reviewId) {

    CustomerReviewDto.Read.Response info = customerReviewService.readReview(restaurantId, menuId,
        reviewId);

    return ResponseEntity.ok(info);
  }

  // 리뷰 수정
  @Transactional
  @PutMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}")
  public ResponseEntity<?> updateReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Long restaurantId,
      @PathVariable Long menuId,
      @PathVariable Long reviewId,
      @Valid @RequestBody CustomerReviewDto.Update.Request form) {

    CustomerReviewDto.Update.Response updated = customerReviewService.updateReview(restaurantId,
        menuId, reviewId, form);

    return ResponseEntity.ok(updated);
  }

  // 리뷰 삭제
  @Transactional
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
