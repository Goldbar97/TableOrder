package kang.tableorder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@Builder
@Entity(name = "ACCOUNT")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Setter
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  private UserEntity userEntity;

  @OneToOne(fetch = FetchType.LAZY)
  private TablesEntity tablesEntity;

  @Builder.Default
  private int balance = 0;

  @CreatedDate
  private LocalDateTime createdAt;
}
