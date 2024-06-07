package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.CartEntity;
import kang.tableorder.entity.CartItemEntity;
import kang.tableorder.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {

  List<CartItemEntity> findAllByCartEntity(CartEntity cartEntity);

  Optional<CartItemEntity> findByIdAndCartEntity(Integer id, CartEntity cartEntity);

  boolean existsByCartEntityAndMenuEntity(CartEntity cartEntity, MenuEntity menuEntity);
}
