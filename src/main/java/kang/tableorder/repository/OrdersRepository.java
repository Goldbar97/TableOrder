package kang.tableorder.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.OrdersEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

  Optional<OrdersEntity> findByIdAndRestaurantEntityId(Long orderId, Long restaurantId);

  List<OrdersEntity> findByRestaurantEntityIdAndRestaurantEntityUserEntity(Long restaurantId,
      UserEntity userEntity);

  Optional<OrdersEntity> findByIdAndRestaurantEntityIdAndRestaurantEntityUserEntity(Long orderId,
      Long restaurantId, UserEntity userEntity);

  Optional<OrdersEntity> findByUserEntityAndCreatedAtAfter(UserEntity userEntity,
      LocalDateTime createdAt);
}