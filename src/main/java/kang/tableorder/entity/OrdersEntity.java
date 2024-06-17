package kang.tableorder.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kang.tableorder.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@Builder
@Entity(name = "ORDERS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Setter
public class OrdersEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @OneToMany(mappedBy = "ordersEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<OrdersItemEntity> orderItemEntities = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private RestaurantEntity restaurantEntity;

  @ManyToOne
  @JoinColumn(name = "tables_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private TablesEntity tablesEntity;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = true)
  private UserEntity userEntity;

  @ManyToOne
  @JoinColumn(name = "account_id", nullable = true)
  private AccountEntity accountEntity;

  @Builder.Default
  private int totalPrice = 0;

  @Builder.Default
  private int visitedCount = 0;

  @CreatedDate
  private LocalDateTime createdAt;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private OrderStatus status = OrderStatus.PENDING;
}