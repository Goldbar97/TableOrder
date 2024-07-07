package kang.tableorder.repository;

import java.util.List;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.OrdersEntity;
import kang.tableorder.entity.OrdersItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersItemRepository extends JpaRepository<OrdersItemEntity, Long> {

  @Modifying
  @Query("DELETE FROM ORDERS_ITEM oi WHERE oi.ordersEntity = :ordersEntity")
  void deleteByOrdersEntity(OrdersEntity ordersEntity);

  @Modifying
  @Query("DELETE FROM ORDERS_ITEM oi WHERE oi.menuEntity = :menuEntity")
  void deleteByMenuEntity(MenuEntity menuEntity);

  List<OrdersItemEntity> findAllByOrdersEntity(OrdersEntity ordersEntity);
}
