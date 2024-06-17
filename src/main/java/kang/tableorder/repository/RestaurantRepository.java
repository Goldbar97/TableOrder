package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

  Optional<RestaurantEntity> findByIdAndUserEntity(Long restaurantId, UserEntity userEntity);

  boolean existsByUserEntity(UserEntity userEntity);

  boolean existsByIdAndUserEntity(Long id, UserEntity userEntity);
}
