//package Main.Repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.awt.print.Pageable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public interface ChatRepository extends JpaRepository {
//
//    @Query(value = "select message from chat where id = :id", nativeQuery = true)
//    List<String> findById (@Param("id") int id, Pageable page);
//}
