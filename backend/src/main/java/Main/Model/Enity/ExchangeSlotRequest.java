package Main.Model.Enity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exchange_slot_request", catalog =  "global_db")
public class ExchangeSlotRequest {

    @Id
    @Column(name ="id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "class_code", referencedColumnName = "class_code")
    private MajorClass majorClass;


    @ManyToOne
    @JoinColumn(name = "student_code", referencedColumnName = "student_code")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "subject_code", referencedColumnName = "subject_code")
    private Subject subject;

    @Column(name = "current_slot")
    private String currentSlot;



    public ExchangeSlotRequest(){}

    public ExchangeSlotRequest(MajorClass majorClass, Account account, Subject subject, String currentSlot){
       this.majorClass = majorClass;
       this.account = account;
       this.subject = subject;
       this.currentSlot = currentSlot;
    }

}
