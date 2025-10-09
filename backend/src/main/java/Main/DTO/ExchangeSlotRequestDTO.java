package Main.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeSlotRequestDTO {

    private String studentCode;
    private String classCode;
    private String currentSlot;
    private String subjectCode;
}
