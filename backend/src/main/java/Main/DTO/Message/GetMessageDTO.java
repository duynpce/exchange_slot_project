package Main.DTO.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMessageDTO {
    private int id;
    private int chatId;
    private int senderId;
    private String content;
}
