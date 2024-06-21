package kang.tableorder.component.deleter;

import kang.tableorder.entity.CartEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartEntityDeleter {

  private final CartRepository cartRepository;
  private final CartItemEntityDeleter cartItemEntityDeleter;

  public void deleteByUserEntity(UserEntity userEntity) {

    CartEntity cartEntity = cartRepository.findByUserEntity(userEntity);

    cartItemEntityDeleter.deleteByCartEntity(cartEntity);

    cartRepository.deleteByCartEntity(cartEntity);
  }

  public void deleteByTablesEntity(TablesEntity tablesEntity) {

    CartEntity cartEntity = cartRepository.findByTablesEntity(tablesEntity);

    cartItemEntityDeleter.deleteByCartEntity(cartEntity);

    cartRepository.deleteByCartEntity(cartEntity);
  }
}
