package kang.tableorder.component.deleter;

import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.repository.VisitedUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisitedUserEntityDeleter {

  private final VisitedUsersRepository visitedUsersRepository;

  public void deleteByUserEntity(UserEntity userEntity) {

    visitedUsersRepository.deleteByUserEntity(userEntity);
  }

  public void deleteByRestaurantEntity(RestaurantEntity restaurantEntity) {

    visitedUsersRepository.deleteByRestaurantEntity(restaurantEntity);
  }
}
