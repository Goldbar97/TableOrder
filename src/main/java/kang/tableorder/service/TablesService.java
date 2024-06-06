package kang.tableorder.service;

import java.util.List;
import kang.tableorder.dto.TablesDto;
import kang.tableorder.entity.RestaurantEntity;
import kang.tableorder.entity.TablesEntity;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.RestaurantRepository;
import kang.tableorder.repository.TablesRepository;
import kang.tableorder.repository.UserRepository;
import kang.tableorder.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TablesService {

  private final RestaurantRepository restaurantRepository;
  private final TokenProvider tokenProvider;
  private final UserRepository userRepository;
  private final TablesRepository tablesRepository;

  // 테이블 등록
  public TablesDto.Create.Response createTables(String header, TablesDto.Create.Request form) {

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

    if (tablesRepository.existsByNumber(form.getNumber())) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_TABLES_NUMBER);
    }

    if (tablesRepository.existsByTabletMacId(form.getTabletMacId())) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_TABLETS_MAC_ID);
    }

    TablesEntity tablesEntity = form.toEntity(restaurantEntity);

    TablesEntity saved = tablesRepository.save(tablesEntity);

    return TablesDto.Create.Response.toDto(saved);
  }

  // 테이블 리스트 조회
  public List<TablesDto.Read.Response> readTables(String header) {
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

    List<TablesEntity> tablesEntities = tablesRepository.findAllBy();

    if (tablesEntities.isEmpty()) {
      throw new CustomException(ErrorCode.NO_TABLES);
    }

    return tablesEntities.stream().map(TablesDto.Read.Response::toDto).toList();
  }

  // 테이블 수정
  public TablesDto.Update.Response updateTables(String header, Integer tablesId,
      TablesDto.Update.Request form) {

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

    TablesEntity tablesEntity = tablesRepository.findById(tablesId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    if (form.getNumber() != null) {
      // 테이블 번호 중복 확인
      if (tablesRepository.existsByNumber(form.getNumber())) {
        throw new CustomException(ErrorCode.ALREADY_EXISTS_TABLES_NUMBER);
      }

      tablesEntity.setNumber(form.getNumber());
    }

    if (form.getTabletMacId() != null) {
      // 이미 다른 테이블에 해당 태블릿 MAC ID 가 있으면 교환
      if (tablesRepository.existsByTabletMacId(form.getTabletMacId())) {
        TablesEntity tablesForSwap = tablesRepository.findByTabletMacId(form.getTabletMacId())
            .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

        tablesForSwap.setTabletMacId(tablesEntity.getTabletMacId());

        tablesRepository.save(tablesForSwap);
      }

      tablesEntity.setTabletMacId(form.getTabletMacId());
    }

    TablesEntity updated = tablesRepository.save(tablesEntity);

    return TablesDto.Update.Response.toDto(updated);
  }

  // 테이블 삭제
  public boolean deleteTables(String header, Integer tablesId) {
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

    TablesEntity tablesEntity = tablesRepository.findById(tablesId)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_TABLES));

    tablesRepository.delete(tablesEntity);

    return true;
  }
}
