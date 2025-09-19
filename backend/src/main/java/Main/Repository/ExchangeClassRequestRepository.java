package Main.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import Main.Model.Enity.ExchangeClassRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ExchangeClassRequestRepository extends JpaRepository<ExchangeClassRequest,Integer> {
    List<ExchangeClassRequest> findByClassCode( String classCode);
    public boolean existsByStudentCode(String studentCode);


}
