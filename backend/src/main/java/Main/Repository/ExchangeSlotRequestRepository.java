package Main.Repository;

import Main.Model.Enity.ExchangeSlotRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeSlotRequestRepository extends JpaRepository<ExchangeSlotRequest,Integer> {
    List<ExchangeSlotRequest> findByMajorClass_ClassCode(String classCode, Pageable pageable);
    List<ExchangeSlotRequest> findBySubject_SubjectCode(String subjectCode, Pageable pageable);
    List<ExchangeSlotRequest> findByMajorClass_ClassCodeAndSubject_SubjectCode(String classCode,String subjectCode, Pageable pageable);
    List<ExchangeSlotRequest> findByMajorClass_Slot(String slot, Pageable pageable);

    boolean existsByAccount_StudentCodeAndSubject_SubjectCode(String studentCode, String subjectCode);
}
