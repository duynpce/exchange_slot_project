package Main.DTO.MajorClass;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMajorClassDTO {
    @NotBlank(message = "blank classCode")
    private String classCode;
    @NotBlank(message = "blank slot")
    private String slot;
}
