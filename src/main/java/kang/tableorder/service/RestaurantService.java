package kang.tableorder.service;

import kang.tableorder.dto.RestaurantDto;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.RestaurantRepository;
import kang.tableorder.repository.UserRepository;
import kang.tableorder.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final TokenProvider tokenProvider;
  private final UserRepository userRepository;

  // 매장 등록
  public RestaurantDto.Create.Response createRestaurant(String header,
      RestaurantDto.Create.Request form) {

    // 매장 존재 유무
    if (restaurantRepository.existsByIdIsNotNull()) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_RESTAURANT);
    }

    String email = tokenProvider.getEmail(header);
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    RestaurantEntity restaurantEntity = form.toEntity();
    restaurantEntity.setUserEntity(userEntity);

    RestaurantEntity saved = restaurantRepository.save(restaurantEntity);

    return RestaurantDto.Create.Response.toDto(saved);
  }

  // 매장 정보 읽기
  public RestaurantDto.Read.Response readRestaurant() {
    RestaurantEntity restaurantEntity = restaurantRepository.findBy()
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    return RestaurantDto.Read.Response.toDto(restaurantEntity);
  }

  // 매장 정보 수정
  public RestaurantDto.Update.Response updateRestaurant(String header,
      RestaurantDto.Update.Request form) {

    // 매장 존재 유무
    if (!restaurantRepository.existsByIdIsNotNull()) {
      throw new CustomException(ErrorCode.NO_RESTAURANT);
    }

    String email = tokenProvider.getEmail(header);
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 점주가 사용자와 일치하는 지 확인
    RestaurantEntity restaurantEntity = restaurantRepository.findByUserEntity(userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    if (StringUtils.hasText(form.getName())) {
      restaurantEntity.setName(form.getName());
    }

    if (StringUtils.hasText(form.getLocation())) {
      restaurantEntity.setLocation(form.getLocation());
    }

    if (StringUtils.hasText(form.getDescription())) {
      restaurantEntity.setDescription(form.getDescription());
    }

    if (StringUtils.hasText(form.getPhoneNumber())) {
      restaurantEntity.setPhoneNumber(form.getPhoneNumber());
    }

    RestaurantEntity saved = restaurantRepository.save(restaurantEntity);

    return RestaurantDto.Update.Response.toDto(saved);
  }

  // 매장 삭제
  public boolean deleteRestaurant(String header) {

    // 매장 존재 유무
    if (!restaurantRepository.existsByIdIsNotNull()) {
      throw new CustomException(ErrorCode.NO_RESTAURANT);
    }

    String email = tokenProvider.getEmail(header);
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 점주가 사용자와 일치하는 지 확인
    RestaurantEntity restaurantEntity = restaurantRepository.findByUserEntity(userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    restaurantRepository.delete(restaurantEntity);

    return true;
  }
}
