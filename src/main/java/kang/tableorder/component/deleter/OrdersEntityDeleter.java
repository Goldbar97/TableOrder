package kang.tableorder.component.deleter;

import kang.tableorder.entity.AccountEntity;
import kang.tableorder.entity.OrdersEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersEntityDeleter {

  private final OrdersRepository ordersRepository;
  private final OrdersItemEntityDeleter ordersItemEntityDeleter;

  public void deleteByUserEntity(UserEntity userEntity) {

    OrdersEntity ordersEntity = ordersRepository.findByUserEntity(userEntity);

    ordersItemEntityDeleter.deleteByOrdersEntity(ordersEntity);

    ordersRepository.deleteByOrdersEntity(ordersEntity);
  }

  public void deleteByTablesEntity(TablesEntity tablesEntity) {

    OrdersEntity ordersEntity = ordersRepository.findByTablesEntity(tablesEntity);

    ordersItemEntityDeleter.deleteByOrdersEntity(ordersEntity);

    ordersRepository.deleteByOrdersEntity(ordersEntity);
  }

  public void deleteByAccountEntity(AccountEntity accountEntity) {

    OrdersEntity ordersEntity = ordersRepository.findByAccountEntity(accountEntity);

    ordersItemEntityDeleter.deleteByOrdersEntity(ordersEntity);

    ordersRepository.deleteByOrdersEntity(ordersEntity);
  }

  public void deleteByRestaurantEntity(RestaurantEntity restaurantEntity) {

    OrdersEntity ordersEntity = ordersRepository.findByRestaurantEntity(restaurantEntity);

    ordersItemEntityDeleter.deleteByOrdersEntity(ordersEntity);

    ordersRepository.deleteByOrdersEntity(ordersEntity);
  }
}
