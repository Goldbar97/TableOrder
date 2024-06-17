package kang.tableorder.service;

import java.util.List;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.OwnerReviewDto;
import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.OwnerReviewEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.CustomerReviewRepository;
import kang.tableorder.repository.MenuRepository;
import kang.tableorder.repository.OwnerReviewRepository;
import kang.tableorder.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerReviewService {

  private final UserEntityGetter userEntityGetter;
  private final CustomerReviewRepository customerReviewRepository;
  private final RestaurantRepository restaurantRepository;
  private final MenuRepository menuRepository;
  private final OwnerReviewRepository ownerReviewRepository;

  // 리뷰 추가
  @Transactional
  public OwnerReviewDto.Create.Response createReview(Long restaurantId, Long menuId,
      Long reviewId, OwnerReviewDto.Create.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(
            ErrorCode.WRONG_OWNER));

    MenuEntity menuEntity = menuRepository.findByIdAndRestaurantEntityIdAndIsAvailableIsTrue(menuId,
            restaurantId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    CustomerReviewEntity customerReviewEntity = customerReviewRepository.findByIdAndMenuEntity(
            reviewId, menuEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_REVIEW));

    OwnerReviewEntity ownerReviewEntity = form.toEntity(restaurantEntity, menuEntity,
        customerReviewEntity, userEntity);

    OwnerReviewEntity saved = ownerReviewRepository.save(ownerReviewEntity);

    return OwnerReviewDto.Create.Response.toDto(saved);
  }

  public List<OwnerReviewDto.Read.Response> readReviewList() {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    List<OwnerReviewEntity> ownerReviewEntities = ownerReviewRepository.findAllByUserEntity(
        userEntity);

    return ownerReviewEntities.stream().map(OwnerReviewDto.Read.Response::toDto).toList();
  }

  public OwnerReviewDto.Read.Response readReview(Long restaurantId, Long menuId,
      Long reviewId, Long ownerReviewId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    OwnerReviewEntity ownerReviewEntity = ownerReviewRepository.findByAllId(ownerReviewId,
            restaurantId,
            menuId, reviewId, userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_REVIEW));

    return OwnerReviewDto.Read.Response.toDto(ownerReviewEntity);
  }

  // 리뷰 수정
  @Transactional
  public OwnerReviewDto.Update.Response updateReview(Long restaurantId, Long menuId,
      Long reviewId, Long ownerReviewId, OwnerReviewDto.Update.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    OwnerReviewEntity ownerReviewEntity = ownerReviewRepository.findByAllId(ownerReviewId,
            restaurantId, menuId, reviewId, userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_REVIEW));

    ownerReviewEntity.setDescription(form.getDescription());

    OwnerReviewEntity updated = ownerReviewRepository.save(ownerReviewEntity);

    return OwnerReviewDto.Update.Response.toDto(updated);
  }

  // 리뷰 삭제
  @Transactional
  public void deleteReview(Long restaurantId, Long menuId, Long reviewId,
      Long ownerReviewId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    ownerReviewRepository.deleteByAllId(ownerReviewId, restaurantId, menuId, reviewId, userEntity);
  }
}