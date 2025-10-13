    package Main.Repository;

    import Main.DTO.LoginRequestDTO;
    import Main.DTO.LoginResponseDTO;
    import jakarta.persistence.Table;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Modifying;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;

    import Main.Model.Enity.Account;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.Optional;

    @Repository
    public interface AccountRepository extends JpaRepository <Account,String>{
//        Optional <Account> findByPhoneNumber(String phoneNumber);
//        Optional <Account> findByAccountName(String accountName);

        Optional <Account> findByStudentCode(String studentCode);
        Optional <Account> findByUsername(String userName);

        boolean existsByPhoneNumber(String phoneNumber);
        boolean existsByUsername(String userName);
        boolean existsByAccountName(String accountName);
        boolean existsByStudentCode(String studentCode);

        @Modifying
        @Transactional
        @Query(value = "update accounts a set a.passwords = :newPassword where a.username = :username",nativeQuery = true)
        int resetPassword(@Param("username") String userName, @Param("newPassword") String newPassword);


    }
