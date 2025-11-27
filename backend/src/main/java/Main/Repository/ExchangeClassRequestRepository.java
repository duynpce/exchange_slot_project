package Main.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import Main.Entity.ExchangeClassRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeClassRequestRepository extends JpaRepository<ExchangeClassRequest,Integer> {
    List<ExchangeClassRequest> findByClassCode(String classCode, Pageable pageable);
    List<ExchangeClassRequest> findByCurrentSlot(String slot, Pageable pageable);


    Optional<ExchangeClassRequest> findByStudentCode(String studentCode);
    Optional <ExchangeClassRequest> findByAccountId(int accountId);

    boolean existsByStudentCode(String studentCode);


}
