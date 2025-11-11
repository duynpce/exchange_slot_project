package Main.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "chat_id")
    private int chatId;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @Column(name = "sender_id")
    private int senderId;

//    @Column(name = "created_at", nullable = false, updatable = false)
//    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Account sender;

    //for insert data
    public Message(int chatId, String content, int senderId){
        this.chatId = chatId;
        this.content = content;
        this.senderId = senderId;
    }


}
