package kang.tableorder.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.AccountEntity;
import kang.tableorder.entity.OrdersEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

  Optional<OrdersEntity> findByIdAndRestaurantEntityId(Long orderId, Long restaurantId);

  Optional<OrdersEntity> findByIdAndRestaurantEntityIdAndTablesEntityTabletMacId(Long orderId,
      Long restaurantId, String tabletMacId);

  List<OrdersEntity> findByRestaurantEntityIdAndRestaurantEntityUserEntity(
      Long restaurantId,
      UserEntity userEntity);

  Optional<OrdersEntity> findByIdAndRestaurantEntityIdAndRestaurantEntityUserEntity(
      Long orderId,
      Long restaurantId, UserEntity userEntity);

  Optional<OrdersEntity> findByUserEntityAndCreatedAtAfter(
      UserEntity userEntity,
      LocalDateTime createdAt);

  @Modifying
  @Query("DELETE FROM ORDERS o WHERE o = :ordersEntity")
  void deleteByOrdersEntity(OrdersEntity ordersEntity);

  OrdersEntity findByUserEntity(UserEntity userEntity);

  OrdersEntity findByTablesEntity(TablesEntity tablesEntity);

  OrdersEntity findByRestaurantEntity(RestaurantEntity restaurantEntity);

  OrdersEntity findByAccountEntity(AccountEntity accountEntity);
}