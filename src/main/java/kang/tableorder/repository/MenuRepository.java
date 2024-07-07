package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

  Optional<MenuEntity> findByIdAndRestaurantEntity(
      Long menuId,
      RestaurantEntity restaurantEntity);

  Optional<MenuEntity> findByIdAndRestaurantEntityIdAndIsAvailableIsTrue(
      Long menuId,
      Long restaurantId);

  Page<MenuEntity> findAllByRestaurantEntityAndIsAvailableIsTrue(
      RestaurantEntity restaurantEntity,
      Pageable pageable);

  boolean existsByNameAndRestaurantEntity(String name, RestaurantEntity restaurantEntity);

  @Modifying
  @Query("DELETE FROM MENU m WHERE m.restaurantEntity = :restaurantEntity")
  void deleteByRestaurantEntity(RestaurantEntity restaurantEntity);

  @Modifying
  @Query("DELETE FROM MENU m WHERE m.id = :id AND m.restaurantEntity = :restaurantEntity")
  void deleteByIdAndRestaurantEntity(Long id, RestaurantEntity restaurantEntity);

  List<MenuEntity> findByRestaurantEntity(RestaurantEntity restaurantEntity);

  @Modifying
  @Query("DELETE FROM MENU m WHERE m in :menuEntities")
  void deleteByMenuEntities(List<MenuEntity> menuEntities);

  @Modifying
  @Query("DELETE FROM MENU m WHERE m = :menuEntity")
  void deleteByMenuEntity(MenuEntity menuEntity);
}