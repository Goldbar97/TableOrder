package kang.tableorder.service;

import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.component.deleter.AccountEntityDeleter;
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

  private final AccountEntityDeleter accountEntityDeleter;
  private final AccountRepository accountRepository;
  private final TablesRepository tablesRepository;
  private final UserEntityGetter userEntityGetter;

  // 계좌 생성
  @Transactional
  public AccountDto.Create.Response createAccount() {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    AccountEntity saved = accountRepository.save(AccountEntity.builder()
        .userEntity(userEntity)
        .build());

    return AccountDto.Create.Response.toDto(saved);
  }

  // 계좌 조회
  @Transactional(readOnly = true)
  public AccountDto.Read.Response readAccount() {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    AccountEntity accountEntity = accountRepository.findByUserEntity(userEntity)
        .orElseThrow(() -> new CustomException(
            ErrorCode.NO_ACCOUNT));

    return AccountDto.Read.Response.toDto(accountEntity, userEntity.getName());
  }

  // 비회원 계좌 조회
  @Transactional(readOnly = true)
  public AccountDto.Read.Response readAccount(
      Long restaurantId, Long tablesId, AccountDto.Read.Request request) {

    TablesEntity tablesEntity = tablesRepository.findByIdAndRestaurantEntityIdAndTabletMacId(
        tablesId, restaurantId,
        request.getTabletMacId()).orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    AccountEntity accountEntity = accountRepository.findByTablesEntity(tablesEntity);

    return AccountDto.Read.Response.toDto(accountEntity, "비회원");
  }

  // 계좌 삭제
  @Transactional
  public void deleteAccount() {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    accountEntityDeleter.deleteByUserEntity(userEntity);
  }

  // 비회원 계좌 삭제
  @Transactional
  public void deleteAccount(
      Long restaurantId, Long tablesId,
      AccountDto.GuestDeposit.Request request) {

    TablesEntity tablesEntity = tablesRepository.findByIdAndRestaurantEntityIdAndTabletMacId(
        tablesId, restaurantId,
        request.getTabletMacId()).orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    accountEntityDeleter.deleteByTablesEntity(tablesEntity);
  }

  // 계좌 입금
  @Transactional
  public AccountDto.UserDeposit.Response depositAccount(AccountDto.UserDeposit.Request request) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    AccountEntity accountEntity = accountRepository.findByUserEntity(userEntity)
        .orElseThrow(() -> new CustomException(
            ErrorCode.NO_ACCOUNT));

    synchronized (accountEntity) {
      accountEntity.setBalance(accountEntity.getBalance() + request.getAmount());
    }

    AccountEntity updated = accountRepository.save(accountEntity);

    return AccountDto.UserDeposit.Response.toDto(updated, userEntity.getName());
  }

  // 비회원 계좌 입금
  @Transactional
  public AccountDto.GuestDeposit.Response depositAccount(
      Long restaurantId, Long tablesId, AccountDto.GuestDeposit.Request request) {

    TablesEntity tablesEntity = tablesRepository.findByIdAndRestaurantEntityIdAndTabletMacId(
        tablesId, restaurantId,
        request.getTabletMacId()).orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    AccountEntity accountEntity = accountRepository.findByTablesEntity(tablesEntity);

    synchronized (accountEntity) {
      accountEntity.setBalance(accountEntity.getBalance() + request.getAmount());
    }

    AccountEntity updated = accountRepository.save(accountEntity);

    return AccountDto.GuestDeposit.Response.toDto(updated, "비회원");
  }
}