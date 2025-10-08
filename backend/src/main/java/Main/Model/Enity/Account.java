package Main.Model.Enity;


import Main.Enum.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "accounts", catalog = "global_db")
@Entity
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generate value , identity --> auto increment
    private int id;

    @Column(name = "username", nullable = false)
    private  String username;

    @Column(name = "passwords",nullable = false)
    private  String password;

    @Column(name = "user_phone_number",nullable = false)
    private  String phoneNumber;

    @Column(name = "account_name",nullable = false)
    private String accountName;

    @Column(name = "student_code",nullable = false)
    private String studentCode;

    @Column(name = "class_code")
    private String classCode;

    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public Account(){

    }
    public  Account(String username, String password, String phoneNumber
            , String accountName,String studentCode, Role role){
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.accountName = accountName;
        this.studentCode = studentCode;
        this.role = role;
    }


}
