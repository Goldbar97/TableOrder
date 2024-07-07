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
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

  List<CartItemEntity> findAllByCartEntity(CartEntity cartEntity);

  Optional<CartItemEntity> findByIdAndCartEntity(Long id, CartEntity cartEntity);

  boolean existsByCartEntityAndMenuEntity(CartEntity cartEntity, MenuEntity menuEntity);

  @Query("SELECT COALESCE(SUM(ci.totalPrice), 0) FROM CART_ITEM ci WHERE ci.cartEntity = " +
      ":cartEntity")
  int findTotalPriceByCartEntity(CartEntity cartEntity);

  @Modifying
  @Query("DELETE FROM CART_ITEM ci WHERE ci.cartEntity = :cartEntity")
  void deleteByCartEntity(CartEntity cartEntity);

  @Modifying
  @Query("DELETE FROM CART_ITEM ci WHERE ci.menuEntity = :menuEntity")
  void deleteByMenuEntity(MenuEntity menuEntity);

  @Modifying
  @Query("DELETE FROM CART_ITEM ci WHERE ci = :cartItemEntity")
  void deleteByCartItemEntity(CartItemEntity cartItemEntity);
}
