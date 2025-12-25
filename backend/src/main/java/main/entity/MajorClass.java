package main.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "class")
@AllArgsConstructor
@NoArgsConstructor
public class MajorClass implements BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "class_code", length = 15, nullable = false)
    private String classCode;

    @Column(name = "slot", length = 3, nullable = false)
    private String slot;

}
