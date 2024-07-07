package kang.tableorder.component.deleter;

import kang.tableorder.entity.CartEntity;
import kang.tableorder.entity.CartItemEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemEntityDeleter {

  private final CartItemRepository cartItemRepository;

  public void deleteByCartEntity(CartEntity cartEntity) {

    cartItemRepository.deleteByCartEntity(cartEntity);
  }

  public void deleteByMenuEntity(MenuEntity menuEntity) {

    cartItemRepository.deleteByMenuEntity(menuEntity);
  }

  public void deleteByCartItemEntity(CartItemEntity cartItemEntity) {

    cartItemRepository.deleteByCartItemEntity(cartItemEntity);
  }
}
