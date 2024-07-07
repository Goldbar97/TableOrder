package kang.tableorder.service;

import java.util.List;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.OrderDto;
import kang.tableorder.entity.AccountEntity;
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
import kang.tableorder.repository.AccountRepository;
import kang.tableorder.repository.CartItemRepository;
import kang.tableorder.repository.CartRepository;
import kang.tableorder.repository.OrdersItemRepository;
import kang.tableorder.repository.OrdersRepository;
import kang.tableorder.repository.RestaurantRepository;
import kang.tableorder.repository.TablesRepository;
import kang.tableorder.repository.VisitedUsersRepository;
import kang.tableorder.type.OrderStatus;
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
  private final AccountRepository accountRepository;
  private final OrdersItemRepository ordersItemRepository;

  // 주문 생성
  // TODO: WebSocket... 매장에 주문 요청 보내기
  @Transactional
  public OrderDto.Create.Response createOrder(OrderDto.Create.Request form) {

    CartEntity cartEntity;
    UserEntity userEntity;
    VisitedUsersEntity visitedUsersEntity = null;

    RestaurantEntity restaurantEntity = restaurantRepository.findById(form.getRestaurantId())
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    TablesEntity tablesEntity = tablesRepository.findByTabletMacId(form.getTabletMacId())
        .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    // 회원일 경우 회원 장바구니, 비회원일 경우 테이블 장바구니
    if (userEntityGetter.isGuest()) {
      userEntity = null;
      cartEntity = cartRepository.findByTablesEntity(tablesEntity);
    } else {
      userEntity = userEntityGetter.getUserEntity();
      cartEntity = cartRepository.findByUserEntity(userEntity);
    }

    if (userEntity != null) {
      visitedUsersEntity = visitedUsersRepository.findByUserEntityAndRestaurantEntityId(
              userEntity, form.getRestaurantId())
          .orElseGet(() -> visitedUsersRepository.save(VisitedUsersEntity.builder()
              .userEntity(userEntity)
              .restaurantEntity(
                  restaurantEntity)
              .visitedCount(0)
              .build())
          );
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
      OrdersItemEntity ordersItemEntity = OrdersItemEntity.builder()
          .menuEntity(cartItem.getMenuEntity())
          .ordersEntity(ordersEntity)
          .count(cartItem.getCount())
          .totalPrice(cartItem.getTotalPrice())
          .build();

      ordersItemRepository.save(ordersItemEntity);
    });

    OrdersEntity saved = ordersRepository.save(ordersEntity);

    cartItemRepository.deleteAll(cartItems);

    cartEntity.setTotalPrice(0);

    cartRepository.save(cartEntity);

    return OrderDto.Create.Response.toDto(saved);
  }

  // 결제
  @Transactional
  public void performPayment(
      Long restaurantId, Long orderId,
      OrderDto.Payment.Request form) {

    AccountEntity accountEntity = getAccountEntity(restaurantId, form.getTabletMacId());

    OrdersEntity ordersEntity = ordersRepository.findById(orderId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_ORDER));

    if (ordersEntity.getStatus() == OrderStatus.ACCEPTED) {
      if (accountEntity.getBalance() >= ordersEntity.getTotalPrice()) {
        accountEntity.setBalance(accountEntity.getBalance() - ordersEntity.getTotalPrice());

        accountRepository.save(accountEntity);

        if (!userEntityGetter.isGuest()) {
          UserEntity userEntity = userEntityGetter.getUserEntity();

          VisitedUsersEntity visitedUsersEntity =
              visitedUsersRepository.findByUserEntityAndRestaurantEntityId(
                      userEntity, restaurantId)
                  .orElseThrow(() -> new CustomException(ErrorCode.NOT_AVAILABLE));

          visitedUsersEntity.setVisitedCount(visitedUsersEntity.getVisitedCount() + 1);

          visitedUsersRepository.save(visitedUsersEntity);
        }

        ordersEntity.setStatus(OrderStatus.COMPLETED);

        ordersEntity.setAccountEntity(accountEntity);

        ordersRepository.save(ordersEntity);
      } else {
        throw new CustomException(ErrorCode.NOT_ENOUGH_BALANCE);
      }
    } else {
      throw new CustomException(ErrorCode.NOT_AVAILABLE_ORDER);
    }
  }

  // 주문 조회
  public OrderDto.Read.Response readOrder(Long restaurantId, Long orderId,
      OrderDto.Read.Request form) {

    OrdersEntity ordersEntity = ordersRepository.findByIdAndRestaurantEntityIdAndTablesEntityTabletMacId(
            orderId, restaurantId, form.getTabletMacId())
        .orElseThrow(() -> new CustomException(ErrorCode.NO_ORDER));

    List<OrdersItemEntity> ordersItemEntities = ordersItemRepository.findAllByOrdersEntity(
        ordersEntity);

    return OrderDto.Read.Response.toDto(ordersEntity, ordersItemEntities);
  }

  // 주문 리스트 조회
  public List<OrderDto.Read.Response> readOrderList(Long restaurantId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    List<OrdersEntity> ordersEntities =
        ordersRepository.findByRestaurantEntityIdAndRestaurantEntityUserEntity(
            restaurantId, userEntity);

    return ordersEntities.stream().map(e -> {
      List<OrdersItemEntity> ordersItemEntities = ordersItemRepository.findAllByOrdersEntity(e);

      return OrderDto.Read.Response.toDto(e, ordersItemEntities);
    }).toList();
  }

  // 환불
  @Transactional
  public void refundPayment(Long restaurantId, Long orderId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    if (!restaurantRepository.existsByIdAndUserEntity(restaurantId, userEntity)) {
      throw new CustomException(ErrorCode.WRONG_OWNER);
    }

    OrdersEntity ordersEntity = ordersRepository.findByIdAndRestaurantEntityId(orderId,
            restaurantId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_ORDER));

    if (ordersEntity.getStatus() == OrderStatus.COMPLETED) {
      AccountEntity accountEntity = ordersEntity.getAccountEntity();

      accountEntity.setBalance(accountEntity.getBalance() + ordersEntity.getTotalPrice());

      accountRepository.save(accountEntity);

      ordersEntity.setStatus(OrderStatus.REFUNDED);

      ordersRepository.save(ordersEntity);
    } else {
      throw new CustomException(ErrorCode.NOT_AVAILABLE);
    }
  }

  // 주문 수정
  @Transactional
  public OrderDto.Update.Response updateOrder(Long restaurantId, Long orderId,
      OrderDto.Update.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    OrdersEntity ordersEntity =
        ordersRepository.findByIdAndRestaurantEntityIdAndRestaurantEntityUserEntity(
                orderId,
                restaurantId, userEntity)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_ORDER));

    ordersEntity.setStatus(form.getStatus());

    OrdersEntity saved = ordersRepository.save(ordersEntity);

    List<OrdersItemEntity> ordersItemEntities = ordersItemRepository.findAllByOrdersEntity(saved);

    return OrderDto.Update.Response.toDto(saved, ordersItemEntities);
  }

  private AccountEntity getAccountEntity(Long restaurantId, String tabletMacId) {

    if (userEntityGetter.isGuest()) {
      TablesEntity tablesEntity = tablesRepository.findByRestaurantEntityIdAndTabletMacId(
              restaurantId, tabletMacId)
          .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

      return accountRepository.findByTablesEntity(tablesEntity);
    } else {
      return accountRepository.findByUserEntity(userEntityGetter.getUserEntity())
          .orElseThrow(() -> new CustomException(ErrorCode.NO_ACCOUNT));
    }
  }
}
