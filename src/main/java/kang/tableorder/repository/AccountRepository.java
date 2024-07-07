package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.AccountEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

  @Modifying
  @Query("DELETE FROM ACCOUNT a WHERE a = :accountEntity")
  void deleteByAccountEntity(AccountEntity accountEntity);

  @Modifying
  @Query("DELETE FROM ACCOUNT a WHERE a.userEntity = :userEntity")
  void deleteByUserEntity(UserEntity userEntity);

  AccountEntity findByTablesEntity(TablesEntity tablesEntity);

  Optional<AccountEntity> findByUserEntity(UserEntity userEntity);
}
