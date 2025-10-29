package Main.Repository;

import Main.Entity.MajorClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MajorClassRepository extends JpaRepository<MajorClass,String> {

    Optional<MajorClass> findByClassCode(String ClassCode);

    boolean existsByClassCode(String classCode);
}
