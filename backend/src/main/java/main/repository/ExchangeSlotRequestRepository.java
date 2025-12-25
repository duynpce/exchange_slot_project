package main.repository;

import main.entity.ExchangeSlotRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeSlotRequestRepository extends JpaRepository<ExchangeSlotRequest,Integer> {
    List<ExchangeSlotRequest> findByCurrentClassCode(String classCode, Pageable pageable);
    List<ExchangeSlotRequest> findByCurrentSlot(String slot, Pageable pageable);

    Optional<ExchangeSlotRequest> findByStudentCode(String studentCode);
    Optional<ExchangeSlotRequest> findByAccountId(int accountId);

    boolean existsByStudentCode(String studentCode);
}
