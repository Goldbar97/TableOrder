package kang.tableorder.component.deleter;

import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.OrdersEntity;
import kang.tableorder.repository.OrdersItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersItemEntityDeleter {

  private final OrdersItemRepository ordersItemRepository;

  public void deleteByOrdersEntity(OrdersEntity ordersEntity) {

    ordersItemRepository.deleteByOrdersEntity(ordersEntity);
  }

  public void deleteByMenuEntity(MenuEntity menuEntity) {

    ordersItemRepository.deleteByMenuEntity(menuEntity);
  }
}