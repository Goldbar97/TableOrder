package kang.tableorder.component.deleter;

import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.OwnerReviewEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.repository.OwnerReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OwnerReviewEntityDeleter {

  private final OwnerReviewRepository ownerReviewRepository;

  public void deleteByUserEntity(UserEntity userEntity) {

    ownerReviewRepository.deleteByUserEntity(userEntity);
  }

  public void deleteByRestaurantEntity(RestaurantEntity restaurantEntity) {

    ownerReviewRepository.deleteByRestaurantEntity(restaurantEntity);
  }

  public void deleteByCustomerReviewEntity(CustomerReviewEntity customerReviewEntity) {

    ownerReviewRepository.deleteByCustomerReviewEntity(customerReviewEntity);
  }

  public void deleteByMenuEntity(MenuEntity menuEntity) {

    ownerReviewRepository.deleteByMenuEntity(menuEntity);
  }

  public void deleteByOwnerReviewEntity(OwnerReviewEntity ownerReviewEntity) {

    ownerReviewRepository.deleteByOwnerReviewEntity(ownerReviewEntity);
  }
}
