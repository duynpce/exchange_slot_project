package Main.Repository;

<<<<<<< HEAD
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
=======
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
>>>>>>> develop


}
