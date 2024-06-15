package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerReviewRepository extends JpaRepository<CustomerReviewEntity, Long> {

  Optional<CustomerReviewEntity> findByIdAndUserEntity(Long id, UserEntity userEntity);

  @Modifying
  @Query("DELETE FROM CUSTOMER_REVIEW cr "
      + "WHERE cr.id = :id "
      + "AND cr.restaurantEntity.id = :restaurantId "
      + "AND cr.menuEntity.id = :menuId "
      + "AND cr.userEntity = :userEntity")
  void deleteByAllId(Long id, Long restaurantId, Long menuId, UserEntity userEntity);

  List<CustomerReviewEntity> findAllByUserEntity(UserEntity userEntity);

  CustomerReviewEntity findTop1ByMenuEntityOrderByCreatedAtDesc(MenuEntity menuEntity);

  Page<CustomerReviewEntity> findAllByMenuEntity(MenuEntity menuEntity, Pageable pageable);

  Optional<CustomerReviewEntity> findByIdAndMenuEntity(Long id, MenuEntity menuEntity);

  @Query("SELECT c FROM CUSTOMER_REVIEW c "
      + "WHERE c.id = :id "
      + "AND c.restaurantEntity.id = :restaurantId "
      + "AND c.menuEntity.id = :menuId "
      + "AND c.userEntity = :userEntity")
  Optional<CustomerReviewEntity> findByAllId(Long id, Long restaurantId, Long menuId,
      UserEntity userEntity);
}