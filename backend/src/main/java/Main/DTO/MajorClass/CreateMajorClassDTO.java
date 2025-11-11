package Main.DTO.MajorClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMajorClassDTO {
    private String classCode;
    private String slot;

}
