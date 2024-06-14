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

  @Transactional
  @PostMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews")
  public ResponseEntity<?> createReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer restaurantId,
      @PathVariable Integer menuId,
      @Valid @RequestBody CustomerReviewDto.Create.Request form) {

    CustomerReviewDto.Create.Response saved = customerReviewService.createReview(restaurantId,
        menuId, form);

    return ResponseEntity.ok(saved);
  }

  @GetMapping("/user/reviews")
  public ResponseEntity<?> readReviewList(
      @RequestHeader("Authorization") String header) {

    List<CustomerReviewDto.Read.Response> info = customerReviewService.readReviewList();

    return ResponseEntity.ok(info);
  }

  @GetMapping("/restaurants/{restaurantId}/menu/{menuId}/reviews/{reviewId}")
  public ResponseEntity<?> readReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer restaurantId,
      @PathVariable Integer menuId,
      @PathVariable Integer reviewId) {

    CustomerReviewDto.Read.Response info = customerReviewService.readReview(restaurantId, menuId,
        reviewId);

    return ResponseEntity.ok(info);
  }

  @Transactional
  @PutMapping("/reviews/{reviewId}")
  public ResponseEntity<?> updateReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer reviewId,
      @Valid @RequestBody CustomerReviewDto.Update.Request form) {

    CustomerReviewDto.Update.Response updated = customerReviewService.updateReview(reviewId, form);

    return ResponseEntity.ok(updated);
  }

  @Transactional
  @DeleteMapping("/reviews/{reviewId}")
  public ResponseEntity<?> deleteReview(
      @RequestHeader("Authorization") String header,
      @PathVariable Integer reviewId) {

    customerReviewService.deleteReview(reviewId);

    return ResponseEntity.ok("삭제되었습니다.");
  }
}
