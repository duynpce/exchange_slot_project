package Main.Model.Enity;


import jakarta.persistence.*;

@Table(name = "accounts", catalog = "users")
@Entity
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generate value , identity --> auto increment
    private int accountID;

    @Column(name = "user_name")
    private  String userName;

    @Column(name = "user_password")
    private  String password;

    @Column(name = "user_phone_number")
    private  String phoneNumber;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "student_code")
    private String studentCode;

    public Account(){

    }
    public  Account(String userName, String password, String phoneNumber, String accountName,String studentCode){
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.accountName = accountName;
        this.studentCode = studentCode;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {

        return userName;
    }

    public  void setPassword(String password){

        this.password = password;
    }

    public String getPassword() {

        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;}

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentCode() {
        return studentCode;
    }
}
