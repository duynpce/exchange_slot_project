package Main.Repository;

import Main.Entity.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {

    @Query("SELECT c FROM Chat c WHERE c.userId1 = :userId OR c.userId2 = :userId")
    List<Chat> findByUserId(@Param("userId") int userId, Pageable pageable);
}
