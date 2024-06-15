package kang.tableorder.service;

import java.util.List;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.CustomerReviewDto;
import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.CustomerReviewRepository;
import kang.tableorder.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerReviewService {

  private final UserEntityGetter userEntityGetter;
  private final MenuRepository menuRepository;
  private final CustomerReviewRepository customerReviewRepository;

  public CustomerReviewDto.Create.Response createReview(Integer restaurantId, Integer menuId,
      CustomerReviewDto.Create.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    MenuEntity menuEntity = menuRepository.findByIdAndRestaurantEntityIdAndIsAvailableIsTrue(
        menuId, restaurantId).orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    CustomerReviewEntity saved = customerReviewRepository.save(
        form.toEntity(menuEntity, userEntity));

    return CustomerReviewDto.Create.Response.toDto(saved);
  }

  public List<CustomerReviewDto.Read.Response> readReviewList() {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    List<CustomerReviewEntity> customerReviewEntities = customerReviewRepository.findAllByUserEntity(
        userEntity);

    return customerReviewEntities.stream().map(CustomerReviewDto.Read.Response::toDto).toList();
  }

  public CustomerReviewDto.Read.Response readReview(Integer restaurantId, Integer menuId,
      Integer reviewId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    CustomerReviewEntity customerReviewEntity = customerReviewRepository.findByAllId(reviewId,
            restaurantId, menuId, userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_REVIEW));

    return CustomerReviewDto.Read.Response.toDto(customerReviewEntity);
  }

  public CustomerReviewDto.Update.Response updateReview(Integer restaurantId, Integer menuId,
      Integer reviewId, CustomerReviewDto.Update.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    CustomerReviewEntity customerReviewEntity = customerReviewRepository.findByAllId(reviewId,
            restaurantId, menuId, userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    customerReviewEntity.setDescription(form.getDescription());

    customerReviewEntity.setRating(form.getRating());

    CustomerReviewEntity updated = customerReviewRepository.save(customerReviewEntity);

    return CustomerReviewDto.Update.Response.toDto(updated);
  }

  public void deleteReview(Integer restaurantId, Integer menuId, Integer reviewId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    customerReviewRepository.deleteByAllId(reviewId, restaurantId, menuId, userEntity);
  }
}
