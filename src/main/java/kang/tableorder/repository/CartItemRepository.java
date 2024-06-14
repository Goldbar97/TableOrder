package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.CartEntity;
import kang.tableorder.entity.CartItemEntity;
import kang.tableorder.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {

  List<CartItemEntity> findAllByCartEntity(CartEntity cartEntity);

  Optional<CartItemEntity> findByIdAndCartEntity(Integer id, CartEntity cartEntity);

  boolean existsByCartEntityAndMenuEntity(CartEntity cartEntity, MenuEntity menuEntity);

  @Modifying
  @Query(value = "DELETE FROM CART_ITEM ci WHERE ci.cartEntity = :cartEntity")
  void deleteAllByCartEntity(CartEntity cartEntity);

  @Modifying
  @Query(value = "DELETE FROM CART_ITEM ci WHERE ci.id = :id AND ci.cartEntity = :cartEntity")
  void deleteByIdAndCartEntity(Integer id, CartEntity cartEntity);

  @Query("SELECT COALESCE(SUM(ci.totalPrice), 0) FROM CART_ITEM ci WHERE ci.cartEntity = :cartEntity")
  int findTotalPriceByCartEntity(CartEntity cartEntity);
}
