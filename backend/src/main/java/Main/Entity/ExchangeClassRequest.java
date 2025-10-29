    package Main.Entity;

    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @Entity
    @Table(name = "exchange_class_request" )
    public class ExchangeClassRequest {

        @Id
        @Column(name = "id")
        @GeneratedValue( strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "student_code" , nullable = false)
        private String studentCode;

        @Column(name = "desired_class",length = 15, nullable = false)
        private String desiredClassCode;

        @Column(name = "current_class",length = 15, nullable = false)
        private String currentClassCode;

        @Column(name = "desired_slot", length = 3, nullable = false)
        private String desiredSlot;

        @Column(name = "current_slot", length = 3, nullable = false)
        private String currentSlot;

        /// those @ManyToOne --> indicate constraint or fk in db --> read-only --> for query data
        @ManyToOne(fetch = FetchType.LAZY) //lazy --> only join table when needed , eager(default) --> always join table
        @JoinColumn(name = "student_code", referencedColumnName = "student_code", insertable = false, updatable = false)
        private Account account;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "desired_class", referencedColumnName = "class_code", insertable = false, updatable = false)
        private MajorClass desiredClass;

        @ManyToOne(fetch =  FetchType.LAZY)
        @JoinColumn(name = "current_class", referencedColumnName = "class_code", insertable = false,updatable = false)
        private MajorClass currentClass;

        //this constructor for insert data
        public ExchangeClassRequest(String studentCode, String desiredClassCode,
                                    String currentClassCode, String desiredSlot, String currentSlot){
            this.studentCode = studentCode;
            this.desiredClassCode = desiredClassCode;
            this.currentClassCode = currentClassCode;
            this.desiredSlot = desiredSlot;
            this.currentSlot = currentSlot;
        }


    }
