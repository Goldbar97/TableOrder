package kang.tableorder.service;

import java.util.ArrayList;
import java.util.List;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.CartDto;
import kang.tableorder.dto.CartItemDto;
import kang.tableorder.entity.CartEntity;
import kang.tableorder.entity.CartItemEntity;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.CartItemRepository;
import kang.tableorder.repository.MenuRepository;
import kang.tableorder.repository.TablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartItemRepository cartItemRepository;
  private final MenuRepository menuRepository;
  private final TablesRepository tablesRepository;
  private final UserEntityGetter userEntityGetter;

  // 카트 아이템 추가
  public void addMenuToCart(Integer restaurantId, Integer menuId, CartDto.Create.Request form) {

    CartEntity cartEntity;

    // 회원일 경우 회원 장바구니, 비회원일 경우 테이블 장바구니
    if (userEntityGetter.isGuest()) {
      cartEntity = tablesRepository.findByTabletMacId(form.getTabletMacId())
          .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES))
          .getCartEntity();
    } else {
      cartEntity = userEntityGetter.getUserEntity().getCartEntity();
    }

    MenuEntity menuEntity = menuRepository.findByIdAndRestaurantEntityIdAndIsAvailableIsTrue(
            menuId, restaurantId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    // 장바구니에 주문하려는 메뉴가 있는지 확인
    if (cartItemRepository.existsByCartEntityAndMenuEntity(cartEntity, menuEntity)) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_MENU);
    }

    CartItemEntity cartItemEntity = CartItemEntity.builder()
        .cartEntity(cartEntity)
        .menuEntity(menuEntity)
        .count(1)
        .totalPrice(menuEntity.getPrice())
        .build();

    cartItemRepository.save(cartItemEntity);
  }

  // 카트 아이템 리스트 조회
  public CartDto.Read.Response readMenuListInCart(CartDto.Read.Request form) {

    CartEntity cartEntity;

    // 회원일 경우 회원 장바구니, 비회원일 경우 테이블 장바구니
    if (userEntityGetter.isGuest()) {
      cartEntity = tablesRepository.findByTabletMacId(form.getTabletMacId())
          .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES))
          .getCartEntity();
    } else {
      cartEntity = userEntityGetter.getUserEntity().getCartEntity();
    }

    List<CartItemDto.Read.Response> cartItems = new ArrayList<>();

    List<CartItemEntity> cartItemEntities = cartItemRepository.findAllByCartEntity(cartEntity);

    int totalPrice = 0;

    for (CartItemEntity cartItem : cartItemEntities) {
      cartItems.add(CartItemDto.Read.Response.toDto(cartItem));

      totalPrice += cartItem.getTotalPrice();
    }

    return CartDto.Read.Response.builder()
        .cartItems(cartItems)
        .totalPrice(totalPrice)
        .build();
  }

  // 카트 아이템 수정
  public CartDto.Update.Response updateMenuInCart(Integer cartItemId,
      CartDto.Update.Request form) {

    CartEntity cartEntity;

    // 회원일 경우 회원 장바구니, 비회원일 경우 테이블 장바구니
    if (userEntityGetter.isGuest()) {
      cartEntity = tablesRepository.findByTabletMacId(form.getTabletMacId())
          .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES))
          .getCartEntity();
    } else {
      cartEntity = userEntityGetter.getUserEntity().getCartEntity();
    }

    CartItemEntity cartItemEntity = cartItemRepository.findByIdAndCartEntity(cartItemId, cartEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_MENU));

    cartItemEntity.setCount(form.getCount());

    int totalPrice = cartItemEntity.getMenuEntity().getPrice() * cartItemEntity.getCount();
    cartItemEntity.setTotalPrice(totalPrice);

    CartItemEntity updated = cartItemRepository.save(cartItemEntity);

    return CartDto.Update.Response.toDto(updated);
  }

  // 카트 아이템 삭제
  public void deleteMenuInCart(Integer cartItemId, CartDto.Delete.Request form) {

    CartEntity cartEntity;

    // 회원일 경우 회원 장바구니, 비회원일 경우 테이블 장바구니
    if (userEntityGetter.isGuest()) {
      cartEntity = tablesRepository.findByTabletMacId(form.getTabletMacId())
          .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES))
          .getCartEntity();
    } else {
      cartEntity = userEntityGetter.getUserEntity().getCartEntity();
    }

    CartItemEntity cartItemEntity = cartItemRepository.findByIdAndCartEntity(cartItemId, cartEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_MENU));

    cartItemRepository.delete(cartItemEntity);
  }

  // 카트 비우기
  public void deleteAllMenuInCart(CartDto.Delete.Request form) {

    CartEntity cartEntity;

    // 회원일 경우 회원 장바구니, 비회원일 경우 테이블 장바구니
    if (userEntityGetter.isGuest()) {
      cartEntity = tablesRepository.findByTabletMacId(form.getTabletMacId())
          .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES))
          .getCartEntity();
    } else {
      cartEntity = userEntityGetter.getUserEntity().getCartEntity();
    }

    List<CartItemEntity> allItems = cartItemRepository.findAllByCartEntity(cartEntity);

    cartItemRepository.deleteAll(allItems);
  }
}
