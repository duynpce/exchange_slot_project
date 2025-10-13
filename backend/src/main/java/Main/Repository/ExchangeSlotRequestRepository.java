package Main.Repository;

import Main.Model.Enity.ExchangeSlotRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeSlotRequestRepository extends JpaRepository<ExchangeSlotRequest,Integer> {
    List<ExchangeSlotRequest> findByAccount_ClassCode(String classCode, Pageable pageable);
    List<ExchangeSlotRequest> findByCurrentSlot(String slot, Pageable pageable);

    boolean existsByAccount_StudentCode(String studentCode);
}
