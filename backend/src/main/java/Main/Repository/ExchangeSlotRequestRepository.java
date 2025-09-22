package Main.Repository;

import Main.Model.Enity.ExchangeSlotRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeSlotRequestRepository extends JpaRepository<ExchangeSlotRequest,Integer> {
    List<ExchangeSlotRequest> findByClassCode(String classCode);
    List<ExchangeSlotRequest> findBySubjectCode(String subjectCode);
    List<ExchangeSlotRequest> findByClassCodeAndSubjectCode(String classCode,String subjectCode);
    List<ExchangeSlotRequest> findBySlot(String slot);
    boolean existsByStudentCodeAndSubjectCode(String studentCode, String subjectCode);
}
