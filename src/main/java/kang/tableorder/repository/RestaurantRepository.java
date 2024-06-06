package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

  boolean existsByIdIsNotNull();

  Optional<RestaurantEntity> findByUserEntity(UserEntity userEntity);

  Optional<RestaurantEntity> findBy();
}
