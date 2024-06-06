package kang.tableorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@Builder
@Entity(name = "CART")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Setter
public class CartEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "tables_id")
  private TablesEntity tablesEntity;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserEntity userEntity;

  @Column(nullable = false)
  private Integer totalPrice;

  @Column(nullable = false)
  private LocalDateTime createdAt;
}
