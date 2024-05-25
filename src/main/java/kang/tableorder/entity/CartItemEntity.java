package kang.tableorder.entity;

import jakarta.persistence.Column;
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
@Entity(name = "CART_ITEM")
@Getter
@NoArgsConstructor
@Setter
public class CartItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "cart_id")
  private CartEntity cartEntity;

  @OneToOne
  @JoinColumn(name = "menu_id")
  private MenuEntity menuEntity;

  @Column(nullable = false)
  private Integer count;

  @Column(nullable = false)
  private Integer totalPrice;
}