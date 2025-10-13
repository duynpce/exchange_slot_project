package Main.Model.Enity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "class")
public class MajorClass {

    @Id
    @Column(name = "class_code", length = 15, nullable = false)
    private String classCode;

    @Column(name = "slot", length = 3)
    private String slot;

}
