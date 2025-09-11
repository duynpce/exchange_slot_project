package Main.Model.Enity;

import jakarta.persistence.*;


@Entity
@Table(name = "exchange_slot_request", catalog =  "global_db")
public class ExchangeSlotRequest {

    @Id
    @Column(name ="exchange_slot_request_id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "student_code", length = 10)
    private String studentCode;

    @Column(name = "current_slot", length = 3 )
    private String slot;

    @Column(name = "class_code", length = 15)
    private String classCode;

    @Column(name = "subject_code", length = 5)
    private String subjectCode;


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

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }
}
