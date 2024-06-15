package kang.tableorder.service;

import java.util.List;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.RestaurantDto;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final UserEntityGetter userEntityGetter;

  // 매장 등록
  public RestaurantDto.Create.Response createRestaurant(RestaurantDto.Create.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    // 매장이 이미 등록됐는지 확인
    if (restaurantRepository.existsByUserEntity(userEntity)) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_RESTAURANT);
    }

    RestaurantEntity saved = restaurantRepository.save(form.toEntity(userEntity));

    return RestaurantDto.Create.Response.toDto(saved);
  }

  // 매장 리스트 정보 읽기
  public List<RestaurantDto.Read.Response> readRestaurantList() {

    List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();

    return restaurantEntities.stream().map(RestaurantDto.Read.Response::toDto).toList();
  }

  // 매장 정보 읽기
  public RestaurantDto.Read.Response readRestaurant(Long restaurantId) {

    RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    return RestaurantDto.Read.Response.toDto(restaurantEntity);
  }

  // 매장 정보 수정
  public RestaurantDto.Update.Response updateRestaurant(Long restaurantId,
      RestaurantDto.Update.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    restaurantEntity.setName(form.getName());

    restaurantEntity.setLocation(form.getLocation());

    restaurantEntity.setDescription(form.getDescription());

    restaurantEntity.setPhoneNumber(form.getPhoneNumber());

    RestaurantEntity saved = restaurantRepository.save(restaurantEntity);

    return RestaurantDto.Update.Response.toDto(saved);
  }

  // 매장 삭제
  public void deleteRestaurant(Long restaurantId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_RESTAURANT));

    restaurantRepository.delete(restaurantEntity);
  }
}