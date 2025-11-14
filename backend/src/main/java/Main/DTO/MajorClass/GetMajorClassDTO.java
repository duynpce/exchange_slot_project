package Main.DTO.MajorClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMajorClassDTO {
    private String Slot;
    private String classCode;
}
