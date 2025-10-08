package Main.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import Main.Model.Enity.ExchangeClassRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ExchangeClassRequestRepository extends JpaRepository<ExchangeClassRequest,Integer> {
    List<ExchangeClassRequest> findByMajorClass_ClassCode(String classCode, Pageable page);
    List<ExchangeClassRequest> findByMajorClass_Slot(String slot, Pageable page);

    public boolean existsByAccount_StudentCode(String studentCode);


}
