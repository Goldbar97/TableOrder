package kang.tableorder.repository;

import kang.tableorder.entity.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

  Page<MenuEntity> findAll(Pageable pageable);

  boolean existsByName(String name);
}