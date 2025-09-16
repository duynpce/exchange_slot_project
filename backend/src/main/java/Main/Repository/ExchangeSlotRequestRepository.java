package Main.Repository;

import Main.Model.Enity.ExchangeClassRequest;
import Main.Model.Enity.ExchangeSlotRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeSlotRequestRepository  extends JpaRepository<ExchangeSlotRequest,Integer> {
    List<ExchangeSlotRequest> findByClassCode(String classCode);
}//co loi
