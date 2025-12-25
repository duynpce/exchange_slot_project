package main.dto.Chat;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatDTO {
    @Min(value = 1, message = "user1Id must be greater than 0")
    private int userId1;

    @Min(value = 1, message = "user2Id must be greater than 0")
    private int userId2;
}
