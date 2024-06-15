package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import kang.tableorder.entity.OwnerReviewEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerReviewRepository extends JpaRepository<OwnerReviewEntity, Integer> {

  List<OwnerReviewEntity> findAllByUserEntity(UserEntity userEntity);

  @Query("SELECT o FROM OWNER_REVIEW o "
      + "WHERE o.id = :id "
      + "AND o.restaurantEntity.id = :restaurantId "
      + "AND o.menuEntity.id = :menuId "
      + "AND o.customerReviewEntity.id = :customerReviewId "
      + "AND o.userEntity = :userEntity")
  Optional<OwnerReviewEntity> findByAllId(Integer id, Integer restaurantId, Integer menuId,
      Integer customerReviewId, UserEntity userEntity);

  @Modifying
  @Query("DELETE FROM OWNER_REVIEW o "
      + "WHERE o.id = :id "
      + "AND o.restaurantEntity.id = :restaurantId "
      + "AND o.menuEntity.id = :menuId "
      + "AND o.customerReviewEntity.id = :customerReviewId "
      + "AND o.userEntity = :userEntity")
  void deleteByAllId(Integer id, Integer restaurantId, Integer menuId, Integer customerReviewId,
      UserEntity userEntity);
}