package Main.DTO.MajorClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMajorClassDTO {
    private String classCode;
    private String slot;
}
