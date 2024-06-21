package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

  Optional<RestaurantEntity> findByIdAndUserEntity(Long restaurantId, UserEntity userEntity);

  boolean existsByUserEntity(UserEntity userEntity);

  boolean existsByIdAndUserEntity(Long id, UserEntity userEntity);

  RestaurantEntity findByUserEntity(UserEntity userEntity);

  @Modifying
  @Query("DELETE FROM RESTAURANT r WHERE r.userEntity = :userEntity")
  void deleteByUserEntity(UserEntity userEntity);

  @Modifying
  @Query("DELETE FROM RESTAURANT r WHERE r = :restaurantEntity")
  void deleteByRestaurantEntity(RestaurantEntity restaurantEntity);
}
