package Main.Model.Enity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "exchange_slot_request", catalog =  "global_db")
public class ExchangeSlotRequest {

    @Id
    @Column(name ="id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "student_code" , nullable = false)
    private String studentCode;

    @Column(name = "current_class",length = 15, nullable = false)
    private String currentClassCode;

    @Column(name = "desired_class", length = 15 , nullable = false)
    private String desiredClasCode;

    @Column(name = "current_slot" , length = 3, nullable = false)
    private String currentSlot;

    @Column(name = "desired_slot", length = 3, nullable = false)
    private String desiredSlot;


    /// those @ManyToOne --> indicate constraint or fk in db --> read-only --> for query data
    @ManyToOne(fetch = FetchType.LAZY) //lazy --> only join table when needed , eager(default) --> always join table
    @JoinColumns({
            @JoinColumn(name = "student_code", referencedColumnName = "student_code", insertable = false, updatable = false),
            @JoinColumn(name = "current_class", referencedColumnName = "class_code", insertable = false,updatable = false)
    })
    private Account account;


    //this constructor for query data
    public ExchangeSlotRequest(Account account, String desiredSlot,  String currentSlot){
        this.account = account;
        this.currentSlot = currentSlot;
        this.desiredSlot = desiredSlot;
    }

    //this constructor for insert data
    public ExchangeSlotRequest(String studentCode, String currentClassCode, String desiredSlot, String currentSlot  ){
        this.studentCode = studentCode;
        this.currentClassCode = currentClassCode;
        this.desiredSlot = desiredSlot;
        this.currentSlot = currentSlot;
    }

}
