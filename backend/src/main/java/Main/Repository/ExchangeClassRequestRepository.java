package Main.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import Main.Model.Enity.ExchangeClassRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExchangeClassRequestRepository extends JpaRepository<ExchangeClassRequest,Integer> {
    List<ExchangeClassRequest> findByAccount_ClassCode(String classCode, Pageable page);
    List<ExchangeClassRequest> findByCurrentSlot(String slot, Pageable page);


    Optional<ExchangeClassRequest> findByAccount_StudentCode(String studentCode);

    public boolean existsByAccount_StudentCode(String studentCode);


}
