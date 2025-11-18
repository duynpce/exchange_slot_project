package Main.Repository;

<<<<<<< HEAD
import Main.Model.Enity.ExchangeSlotRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeSlotRequestRepository extends JpaRepository<ExchangeSlotRequest,Integer> {
    List<ExchangeSlotRequest> findByClassCode(String classCode);
    List<ExchangeSlotRequest> findBySubjectCode(String subjectCode);
    List<ExchangeSlotRequest> findByClassCodeAndSubjectCode(String classCode,String subjectCode);
    boolean existsByStudentCodeAndSubjectCode(String studentCode, String subjectCode);
=======
import Main.Entity.ExchangeSlotRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeSlotRequestRepository extends JpaRepository<ExchangeSlotRequest,Integer> {
    List<ExchangeSlotRequest> findByAccount_ClassCode(String classCode, Pageable pageable);
    List<ExchangeSlotRequest> findByCurrentSlot(String slot, Pageable pageable);

    boolean existsByAccount_StudentCode(String studentCode);
    Optional<ExchangeSlotRequest> findByAccount_StudentCode(String studentCode);
>>>>>>> develop
}
