package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

  Optional<MenuEntity> findByIdAndRestaurantEntity(Integer menuId,
      RestaurantEntity restaurantEntity);

  Optional<MenuEntity> findByIdAndRestaurantEntityIdAndIsAvailableIsTrue(Integer menuId,
      Integer restaurantId);

  Page<MenuEntity> findAllByRestaurantEntityAndIsAvailableIsTrue(RestaurantEntity restaurantEntity,
      Pageable pageable);

  boolean existsByNameAndRestaurantEntity(String name, RestaurantEntity restaurantEntity);
}