    package main.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import main.entity.Account;

    import java.util.Optional;

    @Repository
    public interface AccountRepository extends JpaRepository <Account,Integer>{
//        Optional <Account> findByPhoneNumber(String phoneNumber);
//        Optional <Account> findByAccountName(String accountName);

        Optional <Account> findByStudentCode(String studentCode);
        Optional <Account> findByUsername(String userName);
        Optional <Account> findByEmail(String email);

        boolean existsByPhoneNumber(String phoneNumber);
        boolean existsByUsername(String userName);
        boolean existsByAccountName(String accountName);
        boolean existsByStudentCode(String studentCode);
        boolean existsByEmail(String email);

    }
