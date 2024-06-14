package kang.tableorder.repository;

import kang.tableorder.entity.OwnerReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerReviewRepository extends JpaRepository<OwnerReviewEntity, Integer> {

}
