package Main.DTO.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetChatDTO {
    private int id;
    private int user1Id;
    private int user2Id;
}
