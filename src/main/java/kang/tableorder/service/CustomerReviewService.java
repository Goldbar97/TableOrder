package kang.tableorder.service;

import java.time.LocalDateTime;
import java.util.List;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.component.deleter.CustomerReviewEntityDeleter;
import kang.tableorder.dto.CustomerReviewDto;
import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.OrdersEntity;
import kang.tableorder.entity.OrdersItemEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.entity.VisitedUsersEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.CustomerReviewRepository;
import kang.tableorder.repository.MenuRepository;
import kang.tableorder.repository.OrdersItemRepository;
import kang.tableorder.repository.OrdersRepository;
import kang.tableorder.repository.VisitedUsersRepository;
import kang.tableorder.type.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerReviewService {

  private final UserEntityGetter userEntityGetter;
  private final MenuRepository menuRepository;
  private final CustomerReviewRepository customerReviewRepository;
  private final OrdersRepository ordersRepository;
  private final VisitedUsersRepository visitedUsersRepository;
  private final CustomerReviewEntityDeleter customerReviewEntityDeleter;
  private final OrdersItemRepository ordersItemRepository;

  // 리뷰 추가
  @Transactional
  public CustomerReviewDto.Create.Response createReview(
      Long restaurantId, Long menuId,
      CustomerReviewDto.Create.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    MenuEntity menuEntity = menuRepository.findByIdAndRestaurantEntityIdAndIsAvailableIsTrue(
        menuId, restaurantId).orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    LocalDateTime aWeekAgo = LocalDateTime.now().minusDays(7);

    OrdersEntity ordersEntity = ordersRepository.findByUserEntityAndCreatedAtAfter(
            userEntity, aWeekAgo)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_ORDER));

    if (ordersEntity.getStatus() != OrderStatus.COMPLETED) {
      throw new CustomException(ErrorCode.NOT_AVAILABLE);
    }

    List<OrdersItemEntity> orderItemEntities = ordersItemRepository.findAllByOrdersEntity(
        ordersEntity);

    boolean hasMenu = false;

    for (OrdersItemEntity item : orderItemEntities) {
      if (menuEntity.getId().equals(item.getMenuEntity().getId())) {
        hasMenu = true;
        break;
      }
    }

    if (!hasMenu) {
      throw new CustomException(ErrorCode.NOT_AVAILABLE);
    }

    VisitedUsersEntity visitedUsersEntity =
        visitedUsersRepository.findByUserEntityAndRestaurantEntityId(
                userEntity, restaurantId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_AVAILABLE));

    CustomerReviewEntity saved = customerReviewRepository.save(
        form.toEntity(menuEntity, userEntity));

    return CustomerReviewDto.Create.Response.toDto(saved, visitedUsersEntity.getVisitedCount());
  }

  // 리뷰 리스트 조회
  public List<CustomerReviewDto.Read.Response> readReviewList() {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    List<CustomerReviewEntity> customerReviewEntities =
        customerReviewRepository.findAllByUserEntity(
            userEntity);

    return customerReviewEntities.stream().map(CustomerReviewDto.Read.Response::toDto).toList();
  }

  // 리뷰 조회
  public CustomerReviewDto.Read.Response readReview(
      Long restaurantId, Long menuId,
      Long reviewId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    CustomerReviewEntity customerReviewEntity = customerReviewRepository.findByAllId(reviewId,
            restaurantId,
            menuId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_REVIEW));

    return CustomerReviewDto.Read.Response.toDto(customerReviewEntity);
  }

  // 리뷰 수정
  @Transactional
  public CustomerReviewDto.Update.Response updateReview(
      Long restaurantId, Long menuId,
      Long reviewId, CustomerReviewDto.Update.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    CustomerReviewEntity customerReviewEntity = customerReviewRepository.findByAllId(reviewId,
            restaurantId,
            menuId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    customerReviewEntity.setDescription(form.getDescription());

    customerReviewEntity.setRating(form.getRating());

    CustomerReviewEntity updated = customerReviewRepository.save(customerReviewEntity);

    return CustomerReviewDto.Update.Response.toDto(updated);
  }

  // 리뷰 삭제
  @Transactional
  public void deleteReview(Long restaurantId, Long menuId, Long reviewId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    CustomerReviewEntity customerReviewEntity = customerReviewRepository.findByAllId(reviewId,
            restaurantId,
            menuId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_REVIEW));

    customerReviewEntityDeleter.deleteByCustomerReviewEntity(customerReviewEntity);
  }
}
