package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.OrdersEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

  Optional<OrdersEntity> findByIdAndRestaurantEntityId(Integer orderId, Integer restaurantId);

  List<OrdersEntity> findByRestaurantEntityIdAndRestaurantEntityUserEntity(Integer restaurantId,
      UserEntity userEntity);

  Optional<OrdersEntity> findByIdAndRestaurantEntityIdAndRestaurantEntityUserEntity(Integer orderId,
      Integer restaurantId, UserEntity userEntity);
}