package Main.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat" )
@Data
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id_1")
    private int userId1;

    @Column(name = "user_id_2")
    private int userId2;

    @JoinColumn(name = "user_id_1", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Account user1;

    @JoinColumn(name = "user_id_2", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Account user2;

    // this constructor for insert data
    public Chat(int userId1, int userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }
}
