package kang.tableorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kang.tableorder.type.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@Builder
@Entity(name = "MENU")
@Getter
@NoArgsConstructor
@Setter
public class MenuEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private RestaurantEntity restaurantEntity;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MenuCategory category;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private int spiciness;

  @Column(nullable = false)
  private Boolean isAvailable;
}
