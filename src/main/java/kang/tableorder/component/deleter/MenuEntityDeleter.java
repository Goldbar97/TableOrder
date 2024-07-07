package kang.tableorder.component.deleter;

import java.util.List;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuEntityDeleter {

  private final MenuRepository menuRepository;
  private final CartItemEntityDeleter cartItemEntityDeleter;
  private final CustomerReviewEntityDeleter customerReviewEntityDeleter;
  private final OwnerReviewEntityDeleter ownerReviewEntityDeleter;
  private final OrdersItemEntityDeleter ordersItemEntityDeleter;

  public void deleteByRestaurantEntity(RestaurantEntity restaurantEntity) {

    List<MenuEntity> menuEntities = menuRepository.findByRestaurantEntity(restaurantEntity);

    for (MenuEntity menuEntity : menuEntities) {
      cartItemEntityDeleter.deleteByMenuEntity(menuEntity);

      ownerReviewEntityDeleter.deleteByMenuEntity(menuEntity);

      customerReviewEntityDeleter.deleteByMenuEntity(menuEntity);

      ordersItemEntityDeleter.deleteByMenuEntity(menuEntity);
    }

    menuRepository.deleteByMenuEntities(menuEntities);
  }

  public void deleteByMenuEntity(MenuEntity menuEntity) {

    cartItemEntityDeleter.deleteByMenuEntity(menuEntity);

    ownerReviewEntityDeleter.deleteByMenuEntity(menuEntity);

    customerReviewEntityDeleter.deleteByMenuEntity(menuEntity);

    ordersItemEntityDeleter.deleteByMenuEntity(menuEntity);

    menuRepository.deleteByMenuEntity(menuEntity);
  }
}
