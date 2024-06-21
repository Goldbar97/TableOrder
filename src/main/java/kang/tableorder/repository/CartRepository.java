package kang.tableorder.repository;

import kang.tableorder.entity.CartEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

  @Modifying
  @Query("DELETE FROM CART c WHERE c = :cartEntity")
  void deleteByCartEntity(CartEntity cartEntity);

  CartEntity findByUserEntity(UserEntity userEntity);

  CartEntity findByTablesEntity(TablesEntity tablesEntity);
}
