package Main.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chat" )
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id_1")
    private int userId1;

    @Column(name = "user_id_2")
    private int userId2;


}
