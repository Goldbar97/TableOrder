package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.CustomerReviewEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerReviewRepository extends JpaRepository<CustomerReviewEntity, Integer> {

  Optional<CustomerReviewEntity> findByIdAndUserEntityId(Integer id, Integer userEntityId);

  @Query("DELETE FROM CUSTOMER_REVIEW cr "
      + "WHERE cr.id = :id "
      + "AND cr.userEntity = :userEntity")
  void deleteByIdAndUserEntity(Integer id, UserEntity userEntity);

  List<CustomerReviewEntity> findAllByUserEntityId(Integer userEntityId);
}