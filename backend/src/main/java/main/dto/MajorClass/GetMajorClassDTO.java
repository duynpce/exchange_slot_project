package main.dto.MajorClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMajorClassDTO {
    private String classCode;
    private String slot;
}
