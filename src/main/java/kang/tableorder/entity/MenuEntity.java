package kang.tableorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@AllArgsConstructor
@Builder
@Entity(name = "MENU")
@Getter
@NoArgsConstructor
@Setter
public class MenuEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  private RestaurantEntity restaurantEntity;

  @Column(nullable = false)
  private MenuCategory category;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Integer spiciness;

  @Column(nullable = false)
  private Boolean isAvailable;
}
