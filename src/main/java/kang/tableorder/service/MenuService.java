package kang.tableorder.service;

import java.util.List;
import kang.tableorder.dto.MenuDto;
import kang.tableorder.entity.MenuEntity;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.MenuRepository;
import kang.tableorder.repository.RestaurantRepository;
import kang.tableorder.repository.UserRepository;
import kang.tableorder.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final RestaurantRepository restaurantRepository;
  private final TokenProvider tokenProvider;
  private final UserRepository userRepository;
  private final MenuRepository menuRepository;

  // 메뉴 등록
  public MenuDto.Create.Response createMenu(String header, MenuDto.Create.Request form) {

    // 등록된 매장이 없는 경우
    if (!restaurantRepository.existsByIdIsNotNull()) {
      throw new CustomException(ErrorCode.NO_RESTAURANT);
    }

    String email = tokenProvider.getEmail(header);
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 점주가 사용자와 일치하는 지 확인
    RestaurantEntity restaurantEntity = restaurantRepository.findByUserEntity(userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    // 메뉴 중복 확인
    if (menuRepository.existsByName(form.getName())) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_MENU);
    }

    MenuEntity menuEntity = form.toEntity();
    menuEntity.setRestaurantEntity(restaurantEntity);

    MenuEntity saved = menuRepository.save(menuEntity);

    return MenuDto.Create.Response.toDto(saved);
  }

  // 메뉴 리스트 조회
  // 각 리뷰 하나씩 포함
  public List<MenuDto.Read.Response> readMenus(int page, int size) {

    // 등록된 매장이 없는 경우
    if (!restaurantRepository.existsByIdIsNotNull()) {
      throw new CustomException(ErrorCode.NO_RESTAURANT);
    }

    Pageable pageable = PageRequest.of(page, size);
    Page<MenuEntity> menuEntities = menuRepository.findAll(pageable);

    // 메뉴 없음
    if (menuEntities.isEmpty()) {
      throw new CustomException(ErrorCode.NO_MENU);
    }

    return menuEntities.getContent().stream().map(MenuDto.Read.Response::toDto).toList();
  }

  // 메뉴 자세히 조회
  // 해당 메뉴의 모든 리뷰 조회 가능
  public MenuDto.Read.Response readMenu(Integer menuId) {

    // 등록된 매장이 없는 경우
    if (!restaurantRepository.existsByIdIsNotNull()) {
      throw new CustomException(ErrorCode.NO_RESTAURANT);
    }

    MenuEntity menuEntity = menuRepository.findById(menuId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    if (!menuEntity.getIsAvailable()) {
      throw new CustomException(ErrorCode.NOT_AVAILABLE);
    }

    return MenuDto.Read.Response.toDto(menuEntity);
  }

  // 메뉴 수정
  public MenuDto.Update.Response updateMenu(String header, Integer menuId,
      MenuDto.Update.Request form) {

    // 등록된 매장이 없는 경우
    if (!restaurantRepository.existsByIdIsNotNull()) {
      throw new CustomException(ErrorCode.NO_RESTAURANT);
    }

    String email = tokenProvider.getEmail(header);
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 점주가 사용자와 일치하는 지 확인
    RestaurantEntity restaurantEntity = restaurantRepository.findByUserEntity(userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    MenuEntity menuEntity = menuRepository.findById(menuId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    // 매장에 등록된 메뉴인지 확인
    if (menuEntity.getRestaurantEntity() != restaurantEntity) {
      throw new CustomException(ErrorCode.WRONG_MENU);
    }

    if (form.getIsAvailable() != null) {
      menuEntity.setCategory((form.getCategory()));
    }

    if (StringUtils.hasText(form.getName())) {
      menuEntity.setName(form.getName());
    }

    if (StringUtils.hasText(form.getImageUrl())) {
      menuEntity.setImageUrl(form.getImageUrl());
    }

    if (form.getPrice() != null) {
      menuEntity.setPrice(form.getPrice());
    }

    if (StringUtils.hasText(form.getDescription())) {
      menuEntity.setDescription(form.getDescription());
    }

    if (form.getSpiciness() != null) {
      menuEntity.setSpiciness(form.getSpiciness());
    }

    if (form.getIsAvailable() != null) {
      menuEntity.setIsAvailable(form.getIsAvailable());
    }

    MenuEntity updated = menuRepository.save(menuEntity);

    return MenuDto.Update.Response.builder()
        .id(updated.getId())
        .name(updated.getName())
        .imageUrl(updated.getImageUrl())
        .price(updated.getPrice())
        .description(updated.getDescription())
        .spiciness(updated.getSpiciness())
        .isAvailable(updated.getIsAvailable())
        .build();
  }

  // 메뉴 삭제
  public boolean deleteMenu(String header, Integer menuId) {

    // 등록된 매장이 없는 경우
    if (!restaurantRepository.existsByIdIsNotNull()) {
      throw new CustomException(ErrorCode.NO_RESTAURANT);
    }

    String email = tokenProvider.getEmail(header);
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 점주가 사용자와 일치하는 지 확인
    RestaurantEntity restaurantEntity = restaurantRepository.findByUserEntity(userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    MenuEntity menuEntity = menuRepository.findById(menuId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_MENU));

    // 매장에 등록된 메뉴인지 확인
    if (menuEntity.getRestaurantEntity() != restaurantEntity) {
      throw new CustomException(ErrorCode.WRONG_MENU);
    }

    menuRepository.delete(menuEntity);

    return true;
  }
}
