package kang.tableorder.service;

import java.util.List;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.OrderDto;
import kang.tableorder.entity.CartEntity;
import kang.tableorder.entity.CartItemEntity;
import kang.tableorder.entity.OrdersEntity;
import kang.tableorder.entity.OrdersItemEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.entity.VisitedUsersEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.CartItemRepository;
import kang.tableorder.repository.CartRepository;
import kang.tableorder.repository.OrdersRepository;
import kang.tableorder.repository.RestaurantRepository;
import kang.tableorder.repository.TablesRepository;
import kang.tableorder.repository.VisitedUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final CartItemRepository cartItemRepository;
  private final CartRepository cartRepository;
  private final OrdersRepository ordersRepository;
  private final RestaurantRepository restaurantRepository;
  private final TablesRepository tablesRepository;
  private final UserEntityGetter userEntityGetter;
  private final VisitedUsersRepository visitedUsersRepository;

  // 주문 생성
  // TODO: WebSocket... 매장에 주문 요청 보내기
  @Transactional
  public OrderDto.Create.Response createOrder(Integer restaurantId, OrderDto.Create.Request form) {

    CartEntity cartEntity;
    UserEntity userEntity;
    VisitedUsersEntity visitedUsersEntity = null;

    RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    TablesEntity tablesEntity = tablesRepository.findByTabletMacId(form.getTabletMacId())
        .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    // 회원일 경우 회원 장바구니, 비회원일 경우 테이블 장바구니
    if (userEntityGetter.isGuest()) {
      userEntity = null;
      cartEntity = tablesEntity.getCartEntity();
    } else {
      userEntity = userEntityGetter.getUserEntity();
      cartEntity = userEntity.getCartEntity();
    }

    if (userEntity != null) {
      visitedUsersEntity = visitedUsersRepository.findByUserEntityAndRestaurantEntity(
              userEntity, restaurantEntity)
          .orElseGet(() -> VisitedUsersEntity.builder()
              .userEntity(userEntity)
              .restaurantEntity(restaurantEntity)
              .visitedCount(0)
              .build());
    }

    List<CartItemEntity> cartItems = cartItemRepository.findAllByCartEntity(cartEntity);

    if (cartItems.isEmpty()) {
      throw new CustomException(ErrorCode.NO_CART_ITEM);
    }

    OrdersEntity ordersEntity = OrdersEntity.builder()
        .restaurantEntity(restaurantEntity)
        .tablesEntity(tablesEntity)
        .userEntity(userEntity)
        .totalPrice(cartEntity.getTotalPrice())
        .visitedCount(visitedUsersEntity != null ? visitedUsersEntity.getVisitedCount() : 0)
        .build();

    cartItems.forEach(cartItem -> {
      OrdersItemEntity.builder()
          .menuEntity(cartItem.getMenuEntity())
          .ordersEntity(ordersEntity)
          .count(cartItem.getCount())
          .totalPrice(cartItem.getTotalPrice())
          .build();
    });

    OrdersEntity saved = ordersRepository.save(ordersEntity);

    cartItemRepository.deleteAll(cartItems);

    cartEntity.setTotalPrice(0);

    cartRepository.save(cartEntity);

    return OrderDto.Create.Response.toDto(saved);
  }

  // 주문 조회
  public OrderDto.Read.Response readOrder(Integer restaurantId, Integer orderId,
      OrderDto.Read.Request form) {

    OrdersEntity ordersEntity = ordersRepository.findByIdAndRestaurantEntityId(orderId,
            restaurantId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_ORDER));

    return OrderDto.Read.Response.toDto(ordersEntity);
  }

  // 주문 리스트 조회
  public List<OrderDto.Read.Response> readOrderList(Integer restaurantId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    List<OrdersEntity> ordersEntities = ordersRepository.findByRestaurantEntityIdAndRestaurantEntityUserEntity(
        restaurantId, userEntity);

    return ordersEntities.stream().map(OrderDto.Read.Response::toDto).toList();
  }

  // 주문 수정
  public OrderDto.Update.Response updateOrder(Integer restaurantId, Integer orderId,
      OrderDto.Update.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    OrdersEntity ordersEntity = ordersRepository.findByIdAndRestaurantEntityIdAndRestaurantEntityUserEntity(
        orderId,
        restaurantId, userEntity).orElseThrow(() -> new CustomException(ErrorCode.NO_ORDER));

    ordersEntity.setStatus(form.getStatus());

    return OrderDto.Update.Response.toDto(ordersRepository.save(ordersEntity));
  }
}
