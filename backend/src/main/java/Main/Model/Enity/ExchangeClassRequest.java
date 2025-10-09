package Main.Model.Enity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exchange_class_request",catalog = "global_db" )
public class ExchangeClassRequest {

    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_code", referencedColumnName = "student_code")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "class_code", referencedColumnName = "class_code")
    private MajorClass majorClass;

    @Column(name = "current_slot")
    private String currentSlot;


    public ExchangeClassRequest(){}

    public ExchangeClassRequest(Account account,MajorClass majorClass,String currentSlot){
        this.account = account;
        this.majorClass = majorClass;
        this.currentSlot = currentSlot;
    }


}
