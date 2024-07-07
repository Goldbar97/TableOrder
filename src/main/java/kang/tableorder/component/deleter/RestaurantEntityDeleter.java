package kang.tableorder.component.deleter;

import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantEntityDeleter {

  private final RestaurantRepository restaurantRepository;
  private final MenuEntityDeleter menuEntityDeleter;
  private final OrdersEntityDeleter ordersEntityDeleter;
  private final TablesEntityDeleter tablesEntityDeleter;
  private final CustomerReviewEntityDeleter customerReviewEntityDeleter;
  private final OwnerReviewEntityDeleter ownerReviewEntityDeleter;
  private final VisitedUserEntityDeleter visitedUserEntityDeleter;

  public void deleteByUserEntity(UserEntity userEntity) {

    RestaurantEntity restaurantEntity = restaurantRepository.findByUserEntity(userEntity);

    deleteByRestaurantEntity(restaurantEntity);
  }

  public void deleteByRestaurantEntity(RestaurantEntity restaurantEntity) {

    menuEntityDeleter.deleteByRestaurantEntity(restaurantEntity);

    visitedUserEntityDeleter.deleteByRestaurantEntity(restaurantEntity);

    ownerReviewEntityDeleter.deleteByRestaurantEntity(restaurantEntity);

    tablesEntityDeleter.deleteByRestaurantEntity(restaurantEntity);

    customerReviewEntityDeleter.deleteByRestaurantEntity(restaurantEntity);

    ordersEntityDeleter.deleteByRestaurantEntity(restaurantEntity);

    restaurantRepository.deleteByRestaurantEntity(restaurantEntity);
  }
}