package kang.tableorder.repository;

import kang.tableorder.entity.OrdersItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersitemRepository extends JpaRepository<OrdersItemEntity, Long> {

}
