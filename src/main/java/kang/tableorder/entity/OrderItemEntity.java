package kang.tableorder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Entity(name = "ORDER_ITEM")
@Getter
@NoArgsConstructor
@Setter
public class OrderItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "menu_id")
  private MenuEntity menuEntity;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrderEntity orderEntity;

  private Integer count;

  private Integer totalPrice;
}