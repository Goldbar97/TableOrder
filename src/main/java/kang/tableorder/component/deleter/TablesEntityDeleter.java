package kang.tableorder.component.deleter;

import java.util.List;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.repository.TablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TablesEntityDeleter {

  private final TablesRepository tablesRepository;
  private final AccountEntityDeleter accountEntityDeleter;
  private final CartEntityDeleter cartEntityDeleter;
  private final OrdersEntityDeleter ordersEntityDeleter;

  public void deleteByRestaurantEntity(RestaurantEntity restaurantEntity) {

    List<TablesEntity> tablesEntities = tablesRepository.findAllByRestaurantEntity(restaurantEntity);

    for (TablesEntity tablesEntity : tablesEntities) {
      ordersEntityDeleter.deleteByTablesEntity(tablesEntity);

      accountEntityDeleter.deleteByTablesEntity(tablesEntity);

      cartEntityDeleter.deleteByTablesEntity(tablesEntity);
    }

    tablesRepository.deleteByTablesEntities(tablesEntities);
  }

  public void deleteByTablesEntity(TablesEntity tablesEntity) {

    ordersEntityDeleter.deleteByTablesEntity(tablesEntity);

    accountEntityDeleter.deleteByTablesEntity(tablesEntity);

    cartEntityDeleter.deleteByTablesEntity(tablesEntity);

    tablesRepository.deleteByTablesEntity(tablesEntity);
  }

  public void deleteByTablesEntities(List<TablesEntity> tablesEntities) {

    for (TablesEntity tablesEntity : tablesEntities) {
      ordersEntityDeleter.deleteByTablesEntity(tablesEntity);

      accountEntityDeleter.deleteByTablesEntity(tablesEntity);

      cartEntityDeleter.deleteByTablesEntity(tablesEntity);
    }

    tablesRepository.deleteByTablesEntities(tablesEntities);
  }
}
