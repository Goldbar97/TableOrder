package kang.tableorder.component.deleter;

import kang.tableorder.entity.AccountEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEntityDeleter {

  private final AccountRepository accountRepository;
  private final OrdersEntityDeleter ordersEntityDeleter;

  public void deleteByUserEntity(UserEntity userEntity) {

    AccountEntity accountEntity = accountRepository.findByUserEntity(userEntity)
        .orElseGet(() -> null);

    deleteByAccountEntity(accountEntity);
  }

  public void deleteByTablesEntity(TablesEntity tablesEntity) {

    AccountEntity accountEntity = accountRepository.findByTablesEntity(tablesEntity);

    deleteByAccountEntity(accountEntity);
  }

  public void deleteByAccountEntity(AccountEntity accountEntity) {

    ordersEntityDeleter.deleteByAccountEntity(accountEntity);

    accountRepository.deleteByAccountEntity(accountEntity);
  }
}
