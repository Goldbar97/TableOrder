package kang.tableorder.component.deleter;

import kang.tableorder.entity.UserEntity;
import kang.tableorder.repository.UserRepository;
import kang.tableorder.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEntityDeleter {

  private final AccountEntityDeleter accountEntityDeleter;
  private final CartEntityDeleter cartEntityDeleter;
  private final CustomerReviewEntityDeleter customerReviewEntityDeleter;
  private final OrdersEntityDeleter ordersEntityDeleter;
  private final OwnerReviewEntityDeleter ownerReviewEntityDeleter;
  private final RestaurantEntityDeleter restaurantEntityDeleter;
  private final UserRepository userRepository;
  private final VisitedUserEntityDeleter visitedUserEntityDeleter;

  public void deleteByUserEntity(UserEntity userEntity) {

    if (userEntity.getRole().contains(UserRole.OWNER)) {
      restaurantEntityDeleter.deleteByUserEntity(userEntity);

      ownerReviewEntityDeleter.deleteByUserEntity(userEntity);
    }

    cartEntityDeleter.deleteByUserEntity(userEntity);

    visitedUserEntityDeleter.deleteByUserEntity(userEntity);

    accountEntityDeleter.deleteByUserEntity(userEntity);

    customerReviewEntityDeleter.deleteByUserEntity(userEntity);

    ordersEntityDeleter.deleteByUserEntity(userEntity);

    userRepository.deleteByUserEntity(userEntity);
  }
}