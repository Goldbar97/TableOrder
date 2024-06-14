package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.TablesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TablesRepository extends JpaRepository<TablesEntity, Integer> {

  List<TablesEntity> findAllByRestaurantEntity(RestaurantEntity restaurantEntity);

  Optional<TablesEntity> findByIdAndRestaurantEntity(Integer id, RestaurantEntity restaurantEntity);

  Optional<TablesEntity> findByTabletMacId(String tabletMacId);

  boolean existsByNumberAndRestaurantEntity(Integer number, RestaurantEntity restaurantEntity);

  boolean existsByTabletMacId(String tabletMacId);

  @Modifying
  @Query("DELETE FROM TABLES t WHERE t.id = :id AND t.restaurantEntity = :restaurantEntity")
  void deleteByIdAndRestaurantEntity(Integer id, RestaurantEntity restaurantEntity);

  @Modifying
  @Query("DELETE FROM TABLES t WHERE t.restaurantEntity = :restaurantEntity")
  void deleteAllByRestaurantEntity(RestaurantEntity restaurantEntity);
}