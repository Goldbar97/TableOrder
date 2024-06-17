package kang.tableorder.service;

import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.AccountDto;
import kang.tableorder.entity.AccountEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.AccountRepository;
import kang.tableorder.repository.TablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final UserEntityGetter userEntityGetter;
  private final TablesRepository tablesRepository;

  @Transactional
  public AccountDto.Create.Response createAccount() {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    AccountEntity saved = accountRepository.save(AccountEntity.builder()
        .userEntity(userEntity)
        .build());

    return AccountDto.Create.Response.toDto(saved);
  }

  public AccountDto.Read.Response readAccount() {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    AccountEntity accountEntity = accountRepository.findByUserEntity(userEntity)
        .orElseThrow(() -> new CustomException(
            ErrorCode.NO_ACCOUNT));

    return AccountDto.Read.Response.toDto(accountEntity, userEntity.getName());
  }

  public AccountDto.Read.Response readAccount(Long restaurantId, Long tablesId, AccountDto.Read.Request form) {

    TablesEntity tablesEntity = tablesRepository.findByIdAndRestaurantEntityIdAndTabletMacId(
        tablesId, restaurantId,
        form.getTabletMacId()).orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    AccountEntity accountEntity = tablesEntity.getAccountEntity();

    return AccountDto.Read.Response.toDto(accountEntity, "비회원");
  }

  @Transactional
  public AccountDto.UserDeposit.Response depositAccount(AccountDto.UserDeposit.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    AccountEntity accountEntity = accountRepository.findByUserEntity(userEntity)
        .orElseThrow(() -> new CustomException(
            ErrorCode.NO_ACCOUNT));

    synchronized (accountEntity) {
      accountEntity.setBalance(accountEntity.getBalance() + form.getAmount());
    }

    AccountEntity updated = accountRepository.save(accountEntity);

    return AccountDto.UserDeposit.Response.toDto(updated, userEntity.getName());
  }

  @Transactional
  public AccountDto.GuestDeposit.Response depositAccount(Long restaurantId, Long tablesId, AccountDto.GuestDeposit.Request form) {

    TablesEntity tablesEntity = tablesRepository.findByIdAndRestaurantEntityIdAndTabletMacId(
        tablesId, restaurantId,
        form.getTabletMacId()).orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    AccountEntity accountEntity = tablesEntity.getAccountEntity();

    synchronized (accountEntity) {
      accountEntity.setBalance(accountEntity.getBalance() + form.getAmount());
    }

    AccountEntity updated = accountRepository.save(accountEntity);

    return AccountDto.GuestDeposit.Response.toDto(updated, "비회원");
  }
}
