package kang.tableorder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

  @ManyToOne
  @JoinColumn(name = "menu_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private MenuEntity menuEntity;

  private int count;

  private int totalPrice;
}