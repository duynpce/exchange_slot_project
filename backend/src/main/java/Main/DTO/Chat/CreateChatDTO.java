package Main.DTO.Chat;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatDTO {
    @Min(value = 1, message = "user1Id must be greater than 0")
    private int user1Id;
    @Min(value = 1, message = "user2Id must be greater than 0")
    private int user2Id;
}
