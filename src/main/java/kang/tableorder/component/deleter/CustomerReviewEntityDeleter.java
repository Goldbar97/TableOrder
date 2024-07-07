package kang.tableorder.component.deleter;

import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.repository.CustomerReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerReviewEntityDeleter {

  private final CustomerReviewRepository customerReviewRepository;
  private final OwnerReviewEntityDeleter ownerReviewEntityDeleter;

  public void deleteByUserEntity(UserEntity userEntity) {

    CustomerReviewEntity customerReviewEntity = customerReviewRepository.findByUserEntity(
        userEntity);

    deleteByCustomerReviewEntity(customerReviewEntity);
  }

  public void deleteByMenuEntity(MenuEntity menuEntity) {

    CustomerReviewEntity customerReviewEntity = customerReviewRepository.findByMenuEntity(
        menuEntity);

    deleteByCustomerReviewEntity(customerReviewEntity);
  }

  public void deleteByRestaurantEntity(RestaurantEntity restaurantEntity) {

    CustomerReviewEntity customerReviewEntity = customerReviewRepository.findByRestaurantEntity(
        restaurantEntity);

    deleteByCustomerReviewEntity(customerReviewEntity);
  }

  public void deleteByCustomerReviewEntity(CustomerReviewEntity customerReviewEntity) {

    ownerReviewEntityDeleter.deleteByCustomerReviewEntity(customerReviewEntity);

    customerReviewRepository.deleteByCustomerReviewEntity(customerReviewEntity);
  }
}
