package kang.tableorder.service;

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
import kang.tableorder.repository.CartRepository;
import kang.tableorder.repository.MenuRepository;
import kang.tableorder.repository.TablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartItemRepository cartItemRepository;
  private final CartRepository cartRepository;
  private final MenuRepository menuRepository;
  private final TablesRepository tablesRepository;
  private final UserEntityGetter userEntityGetter;

  // 카트 아이템 추가
  @Transactional
  public void addMenuToCart(Long restaurantId, Long menuId, CartDto.Create.Request form) {

    CartEntity cartEntity = getCartEntity(form.getTabletMacId());

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
        .count(form.getCount())
        .totalPrice(menuEntity.getPrice() * form.getCount())
        .build();

    cartItemRepository.save(cartItemEntity);

    cartEntity.setTotalPrice(cartItemRepository.findTotalPriceByCartEntity(cartEntity));

    cartRepository.save(cartEntity);
  }

  // 카트 아이템 리스트 조회
  public CartDto.Read.Response readMenuListInCart(CartDto.Read.Request form) {

    CartEntity cartEntity = getCartEntity(form.getTabletMacId());

    List<CartItemEntity> cartItemEntities = cartItemRepository.findAllByCartEntity(cartEntity);

    return CartDto.Read.Response.builder()
        .cartItems(cartItemEntities.stream().map(CartItemDto.Read.Response::toDto).toList())
        .totalPrice(cartEntity.getTotalPrice())
        .build();
  }

  // 카트 아이템 수정
  @Transactional
  public CartDto.Update.Response updateMenuInCart(Long cartItemId,
      CartDto.Update.Request form) {

    CartEntity cartEntity = getCartEntity(form.getTabletMacId());

    CartItemEntity cartItemEntity = cartItemRepository.findByIdAndCartEntity(cartItemId, cartEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_MENU));

    cartItemEntity.setCount(form.getCount());

    int totalPrice = cartItemEntity.getMenuEntity().getPrice() * cartItemEntity.getCount();
    cartItemEntity.setTotalPrice(totalPrice);

    CartItemEntity updated = cartItemRepository.save(cartItemEntity);

    cartEntity.setTotalPrice(cartItemRepository.findTotalPriceByCartEntity(cartEntity));

    cartRepository.save(cartEntity);

    return CartDto.Update.Response.toDto(updated);
  }

  // 카트 아이템 삭제
  @Transactional
  public void deleteMenuInCart(Long cartItemId, CartDto.Delete.Request form) {

    CartEntity cartEntity = getCartEntity(form.getTabletMacId());

    cartItemRepository.deleteByIdAndCartEntity(cartItemId, cartEntity);

    cartEntity.setTotalPrice(cartItemRepository.findTotalPriceByCartEntity(cartEntity));

    cartRepository.save(cartEntity);
  }

  // 카트 비우기
  @Transactional
  public void deleteAllMenuInCart(CartDto.Delete.Request form) {

    CartEntity cartEntity = getCartEntity(form.getTabletMacId());

    cartItemRepository.deleteAllByCartEntity(cartEntity);

    cartEntity.setTotalPrice(cartItemRepository.findTotalPriceByCartEntity(cartEntity));

    cartRepository.save(cartEntity);
  }

  // 회원일 경우 회원 장바구니, 비회원일 경우 테이블 장바구니
  private CartEntity getCartEntity(String tabletMacId) {

    if (userEntityGetter.isGuest()) {
      return tablesRepository.findByTabletMacId(tabletMacId)
          .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES))
          .getCartEntity();
    } else {
      return userEntityGetter.getUserEntity().getCartEntity();
    }
  }
}