package kang.tableorder.service;

import java.util.List;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.TablesDto;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.RestaurantRepository;
import kang.tableorder.repository.TablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TablesService {

  private final RestaurantRepository restaurantRepository;
  private final TablesRepository tablesRepository;
  private final UserEntityGetter userEntityGetter;

  // 테이블 등록
  public TablesDto.Create.Response createTables(Long restaurantId,
      TablesDto.Create.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    // 번호 중복 확인
    if (tablesRepository.existsByNumberAndRestaurantEntity(form.getNumber(), restaurantEntity)) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_TABLES_NUMBER);
    }

    // 태블릿 MAC ID 중복 확인
    if (tablesRepository.existsByTabletMacId(form.getTabletMacId())) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_TABLETS_MAC_ID);
    }

    TablesEntity saved = tablesRepository.save(form.toEntity(restaurantEntity));

    return TablesDto.Create.Response.toDto(saved);
  }

  // 테이블 리스트 조회
  public List<TablesDto.Read.Response> readTablesList(Long restaurantId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    List<TablesEntity> tablesEntities = tablesRepository.findAllByRestaurantEntity(
        restaurantEntity);

    return tablesEntities.stream().map(TablesDto.Read.Response::toDto).toList();
  }

  // 테이블 조회
  public TablesDto.Read.Response readTables(Long restaurantId, Long tablesId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    TablesEntity tablesEntity = tablesRepository.findByIdAndRestaurantEntity(tablesId,
            restaurantEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    return TablesDto.Read.Response.toDto(tablesEntity);
  }

  // 테이블 수정
  public TablesDto.Update.Response updateTables(Long restaurantId, Long tablesId,
      TablesDto.Update.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    TablesEntity tablesEntity = tablesRepository.findByIdAndRestaurantEntity(tablesId,
            restaurantEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    // 테이블 번호 중복 확인
    if (tablesRepository.existsByNumberAndRestaurantEntity(form.getNumber(), restaurantEntity)) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_TABLES_NUMBER);
    }

    tablesEntity.setNumber(form.getNumber());

    // 이미 다른 테이블에 해당 태블릿 MAC ID 가 있으면 교환
    if (tablesRepository.existsByTabletMacId(form.getTabletMacId())) {
      TablesEntity tablesForSwap = tablesRepository.findByTabletMacId(form.getTabletMacId())
          .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

      tablesForSwap.setTabletMacId(tablesEntity.getTabletMacId());

      tablesRepository.save(tablesForSwap);
    }

    tablesEntity.setTabletMacId(form.getTabletMacId());

    TablesEntity updated = tablesRepository.save(tablesEntity);

    return TablesDto.Update.Response.toDto(updated);
  }

  // 테이블 삭제
  public void deleteTables(Long restaurantId, Long tablesId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    tablesRepository.deleteByIdAndRestaurantEntity(tablesId, restaurantEntity);
  }

  // 테이블 리스트 삭제
  public void deleteTablesList(Long restaurantId) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndUserEntity(restaurantId,
            userEntity)
        .orElseThrow(() -> new CustomException(ErrorCode.WRONG_OWNER));

    tablesRepository.deleteAllByRestaurantEntity(restaurantEntity);
  }
}