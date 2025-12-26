package main.dto.Message;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageDTO {
    @Min(value = 1, message = "chatId must be greater than 0")
    private int chatId;
    @Min(value = 1, message = "chatId must be greater than 0")
    private int senderId;

    @NotBlank(message = "blank content") @Max(value = 1000, message = "content too long")
    private String content;
}
