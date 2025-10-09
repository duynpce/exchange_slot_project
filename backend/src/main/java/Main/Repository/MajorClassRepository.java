package Main.Repository;

import Main.Model.Enity.MajorClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MajorClassRepository extends JpaRepository {

    Optional<MajorClass> findByClassCode(String ClassCode);
}
