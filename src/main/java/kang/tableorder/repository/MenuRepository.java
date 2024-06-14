package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

  Optional<MenuEntity> findByIdAndRestaurantEntity(Integer menuId,
      RestaurantEntity restaurantEntity);

  Optional<MenuEntity> findByIdAndRestaurantEntityIdAndIsAvailableIsTrue(Integer menuId,
      Integer restaurantId);

  Page<MenuEntity> findAllByRestaurantEntityAndIsAvailableIsTrue(RestaurantEntity restaurantEntity,
      Pageable pageable);

  boolean existsByNameAndRestaurantEntity(String name, RestaurantEntity restaurantEntity);

  @Modifying
  @Query("DELETE FROM MENU m WHERE m.id = :id AND m.restaurantEntity = :restaurantEntity")
  void deleteByIdAndRestaurantEntity(Integer id, RestaurantEntity restaurantEntity);
}