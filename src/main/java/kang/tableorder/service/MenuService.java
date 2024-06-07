package kang.tableorder.service;

import java.util.List;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.MenuDto;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.MenuRepository;
import kang.tableorder.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;
  private final RestaurantRepository restaurantRepository;
  private final UserEntityGetter userEntityGetter;

  // 메뉴 등록
  public MenuDto.Create.Response createMenu(Integer restaurantId, MenuDto.Create.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    // 메뉴 중복 확인
    if (menuRepository.existsByNameAndRestaurantEntity(form.getName(), restaurantEntity)) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_MENU);
    }

    MenuEntity saved = menuRepository.save(form.toEntity(restaurantEntity));

    return MenuDto.Create.Response.toDto(saved);
  }

  // 메뉴 리스트 조회
  // TODO: 각 메뉴의 리뷰 하나씩 포함하기
  public List<MenuDto.Read.Response> readMenus(Integer restaurantId, int page, int size) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    Pageable pageable = PageRequest.of(page, size);

    Page<MenuEntity> menuEntities = menuRepository.findAllByRestaurantEntityAndIsAvailableIsTrue(
        restaurantEntity, pageable);

    return menuEntities.getContent().stream().map(MenuDto.Read.Response::toDto).toList();
  }

  // 메뉴 자세히 조회
  // TODO: 해당 메뉴의 모든 리뷰 조회 가능
  public MenuDto.Read.Response readMenu(Integer restaurantId, Integer menuId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    MenuEntity menuEntity = menuRepository.findByIdAndRestaurantEntity(menuId, restaurantEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    return MenuDto.Read.Response.toDto(menuEntity);
  }

  // 메뉴 수정
  public MenuDto.Update.Response updateMenu(Integer restaurantId, Integer menuId,
      MenuDto.Update.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    MenuEntity menuEntity = menuRepository.findByIdAndRestaurantEntity(menuId, restaurantEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    menuEntity.setCategory((form.getCategory()));

    menuEntity.setName(form.getName());

    menuEntity.setImageUrl(form.getImageUrl());

    menuEntity.setPrice(form.getPrice());

    menuEntity.setDescription(form.getDescription());

    menuEntity.setSpiciness(form.getSpiciness());

    menuEntity.setIsAvailable(form.getIsAvailable());

    MenuEntity updated = menuRepository.save(menuEntity);

    return MenuDto.Update.Response.toDto(updated);
  }

  // 메뉴 삭제
  public void deleteMenu(Integer restaurantId, Integer menuId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    MenuEntity menuEntity = menuRepository.findByIdAndRestaurantEntity(menuId, restaurantEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    menuRepository.delete(menuEntity);
  }
}
