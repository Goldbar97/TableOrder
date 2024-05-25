package kang.tableorder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "ORDER")
@Getter
@NoArgsConstructor
@Setter
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  private TableEntity tableId;

  @ManyToOne
  private UserEntity userId;

  @ManyToOne
  private GuestEntity guestId;

  @CreatedDate
  private LocalDateTime createdAt;

  private Enum<OrderStatus> status;
}