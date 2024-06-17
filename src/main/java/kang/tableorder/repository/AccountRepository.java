package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.AccountEntity;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

  Optional<AccountEntity> findByUserEntity(UserEntity userEntity);
}
