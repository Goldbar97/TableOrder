package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);

  Optional<UserEntity> findByEmail(String email);
}
