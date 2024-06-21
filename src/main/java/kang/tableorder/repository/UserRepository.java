package kang.tableorder.repository;

import java.util.Optional;
import kang.tableorder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query(value = "SELECT u FROM USER u LEFT JOIN FETCH u.role WHERE u.email = :email")
  Optional<UserEntity> findByEmailWithRole(@Param("email") String email);

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);

  @Modifying
  @Query("DELETE FROM USER u WHERE u = :userEntity")
  void deleteByUserEntity(UserEntity userEntity);
}
