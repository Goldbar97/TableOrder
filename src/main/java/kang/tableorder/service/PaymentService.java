package kang.tableorder.service;

import kang.tableorder.repository.VisitedUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final VisitedUsersRepository visitedUsersRepository;

//  public void performPayment() {
//
//    VisitedUsersEntity visitedUsersEntity = visitedUsersRepository.findByUserEntityAndRestaurantEntity(
//            userEntity, restaurantEntity)
//        .orElseGet(() -> VisitedUsersEntity.builder()
//            .userEntity(userEntity)
//            .restaurantEntity(restaurantEntity)
//            .visitedCount(0)
//            .build());
//
//    visitedUsersEntity.setVisitedCount(visitedUsersEntity.getVisitedCount() + 1);
//
//    visitedUsersRepository.save(visitedUsersEntity);
//  }
}
