package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.OwnerReviewEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerReviewRepository extends JpaRepository<OwnerReviewEntity, Long> {

  List<OwnerReviewEntity> findAllByUserEntity(UserEntity userEntity);

  @Query("SELECT o FROM OWNER_REVIEW o "
      + "WHERE o.id = :id "
      + "AND o.restaurantEntity.id = :restaurantId "
      + "AND o.menuEntity.id = :menuId "
      + "AND o.customerReviewEntity.id = :customerReviewId "
      + "AND o.userEntity = :userEntity")
  Optional<OwnerReviewEntity> findByAllId(
      Long id, Long restaurantId, Long menuId,
      Long customerReviewId, UserEntity userEntity);

  @Modifying
  @Query("DELETE FROM OWNER_REVIEW o WHERE o.userEntity = :userEntity")
  void deleteByUserEntity(UserEntity userEntity);

  @Modifying
  @Query("DELETE FROM OWNER_REVIEW o WHERE o.restaurantEntity = :restaurantEntity")
  void deleteByRestaurantEntity(RestaurantEntity restaurantEntity);

  @Modifying
  @Query("DELETE FROM OWNER_REVIEW o WHERE o.customerReviewEntity = :customerReviewEntity")
  void deleteByCustomerReviewEntity(CustomerReviewEntity customerReviewEntity);

  @Modifying
  @Query("DELETE FROM OWNER_REVIEW o WHERE o.menuEntity = :menuEntity")
  void deleteByMenuEntity(MenuEntity menuEntity);

  @Modifying
  @Query("DELETE FROM OWNER_REVIEW o WHERE o = :ownerReviewEntity")
  void deleteByOwnerReviewEntity(OwnerReviewEntity ownerReviewEntity);
}