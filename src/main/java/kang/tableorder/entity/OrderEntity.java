package kang.tableorder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import kang.tableorder.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@AllArgsConstructor
@Builder
@Entity(name = "`ORDER`")
@Getter
@NoArgsConstructor
@Setter
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "tables_id")
  private TablesEntity tablesEntity;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity userEntity;

  @ManyToOne
  @JoinColumn(name = "guest_id")
  private GuestEntity guestEntity;

  @CreatedDate
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;
}