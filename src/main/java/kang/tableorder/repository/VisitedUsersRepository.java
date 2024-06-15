package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.entity.VisitedUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedUsersRepository extends JpaRepository<VisitedUsersEntity, Long> {

  Optional<VisitedUsersEntity> findByUserEntityAndRestaurantEntity(UserEntity userEntity,
      RestaurantEntity restaurantEntity);
}
