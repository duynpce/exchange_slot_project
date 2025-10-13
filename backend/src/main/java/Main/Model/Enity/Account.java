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

    @Column(name = "phone_number",nullable = false)
    private  String phoneNumber;

    @Column(name = "account_name",nullable = false)
    private String accountName;

    @Column(name = "student_code",nullable = false)
    private String studentCode;

    @Column(name = "class_code",length = 15, nullable = false)
    private String classCode;   

    @ManyToOne
    @JoinColumn(name = "class_code", referencedColumnName = "class_code", insertable = false, updatable = false)
    private MajorClass majorClass;

    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public Account(){

    }

    //for query data
    public  Account(String username, String password, String phoneNumber
            , String accountName,String studentCode, MajorClass majorClass,Role role){
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.accountName = accountName;
        this.studentCode = studentCode;
        this.majorClass = majorClass;
        this.role = role;
    }

    //for insert data
    public Account(String username, String password, String phoneNumber
            , String accountName,String studentCode, String classCode,Role role){
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.accountName = accountName;
        this.studentCode = studentCode;
        this.classCode = classCode;
        this.role = role;
    }


}
