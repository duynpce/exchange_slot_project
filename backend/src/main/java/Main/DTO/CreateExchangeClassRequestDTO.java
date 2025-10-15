package Main.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExchangeClassRequestDTO {

    private String studentCode;
    private String desiredClassCode;
}
