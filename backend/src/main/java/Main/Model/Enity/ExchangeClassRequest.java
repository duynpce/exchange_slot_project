package Main.Model.Enity;

import jakarta.persistence.*;

@Entity
@Table(name = "exchange_class_request",catalog = "global_db" )
public class ExchangeClassRequest {

    @Id
    @Column(name ="exchange_class_request_id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "student_code", length = 10)
    private String studentCode;

    @Column(name = "current_slot", length = 3)
    private String slot;

    @Column(name = "class_code", length = 15)
    private String classCode;

    public ExchangeClassRequest(){}

    public ExchangeClassRequest(String studentCode, String slot, String classCode){
        this.studentCode = studentCode;
        this.slot = slot;
        this.classCode = classCode;
    }

    public int getID() {
        return ID;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getSlot() {
        return slot;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassCode() {
        return classCode;
    }
}
