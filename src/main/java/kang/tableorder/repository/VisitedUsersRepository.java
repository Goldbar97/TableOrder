package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.entity.VisitedUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedUsersRepository extends JpaRepository<VisitedUsersEntity, Long> {

  Optional<VisitedUsersEntity> findByUserEntityAndRestaurantEntityId(
      UserEntity userEntity,
      Long restaurantEntityId);

  @Modifying
  @Query("DELETE FROM VISITED_USERS vu WHERE vu.userEntity = :userEntity")
  void deleteByUserEntity(UserEntity userEntity);

  @Modifying
  @Query("DELETE FROM VISITED_USERS vu WHERE vu.restaurantEntity = :restaurantEntity")
  void deleteByRestaurantEntity(RestaurantEntity restaurantEntity);
}
