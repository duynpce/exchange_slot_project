package Main.DTO.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseMessageDTO {
    private int id;
    private int chatId;
    private String content;
    private int senderId;
}
