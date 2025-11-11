package Main.DTO.ExchangeClassRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExchangeClassRequestDTO {
    private String desiredClassCode;

}

