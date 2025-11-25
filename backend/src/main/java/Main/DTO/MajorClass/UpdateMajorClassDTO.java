package Main.DTO.MajorClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMajorClassDTO {
    private int id;
    private String classCode;
    private String slot;
}
