package Main.Repository;

import Main.Model.Enity.ExchangeSlotRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeSlotRepository extends JpaRepository<ExchangeSlotRequest,Integer> {
}
