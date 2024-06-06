package kang.tableorder.repository;

import java.util.List;
import java.util.Optional;
import kang.tableorder.entity.TablesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TablesRepository extends JpaRepository<TablesEntity, Integer> {

  List<TablesEntity> findAllBy();

  boolean existsByNumber(Integer number);

  boolean existsByTabletMacId(String tabletMacId);

  Optional<TablesEntity> findByTabletMacId(String tabletMacId);
}
