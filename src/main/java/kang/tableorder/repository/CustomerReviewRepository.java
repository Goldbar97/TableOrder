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

public interface CustomerReviewRepository extends JpaRepository<CustomerReviewEntity, Integer> {

  Optional<CustomerReviewEntity> findByIdAndUserEntityId(Integer id, Integer userEntityId);

  @Modifying
  @Query("DELETE FROM CUSTOMER_REVIEW cr "
      + "WHERE cr.id = :id "
      + "AND cr.userEntity = :userEntity")
  void deleteByIdAndUserEntity(Integer id, UserEntity userEntity);

  List<CustomerReviewEntity> findAllByUserEntityId(Integer userEntityId);

  Optional<CustomerReviewEntity> findTop1ByMenuEntityOrderByCreatedAtDesc(MenuEntity menuEntity);

  Page<CustomerReviewEntity> findAllByMenuEntity(MenuEntity menuEntity, Pageable pageable);

  Optional<CustomerReviewEntity> findByIdAndMenuEntity(Integer id, MenuEntity menuEntity);
}