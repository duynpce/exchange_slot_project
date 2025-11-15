package Main.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import Main.Entity.ExchangeClassRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeClassRequestRepository extends JpaRepository<ExchangeClassRequest,Integer> {
    List<ExchangeClassRequest> findByAccount_ClassCode(String classCode, Pageable page);
    List<ExchangeClassRequest> findByCurrentSlot(String slot, Pageable page);


    Optional<ExchangeClassRequest> findByAccount_StudentCode(String studentCode);

    boolean existsByAccount_StudentCode(String studentCode);


}
