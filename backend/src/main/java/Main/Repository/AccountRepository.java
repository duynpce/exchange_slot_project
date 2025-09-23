    package Main.Repository;

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
        Optional <Account> findByPhoneNumber(String phoneNumber);
        Optional <Account> findByUserName(String userName);
        Optional <Account> findByAccountName(String accountName);
        Optional <Account> findByStudentCode(String studentCode);

        @Modifying
        @Transactional
        @Query(value = "update accounts a set a.user_password = :newPassword where a.user_name = :user_name",nativeQuery = true)
        int resetPassword(@Param("user_name") String userName, @Param("newPassword") String newPassword);


    }
