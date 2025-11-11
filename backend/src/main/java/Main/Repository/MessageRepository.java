package Main.Repository;

import Main.Entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    // find by chat id and descending by message id
    List<Message> findByChatIdOrderByIdDesc(int chatId, Pageable pageable);

}
