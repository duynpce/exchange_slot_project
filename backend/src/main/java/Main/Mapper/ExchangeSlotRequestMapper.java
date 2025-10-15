package Main.Mapper;

import Main.DTO.CreateExchangeSlotRequestDTO;
import Main.DTO.ExchangeSlotRequestResponseDTO;
import Main.Model.Enity.ExchangeSlotRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExchangeSlotRequestMapper {

    ExchangeSlotRequestResponseDTO toDto(ExchangeSlotRequest request);
    List<ExchangeSlotRequestResponseDTO> toDtoList(List<ExchangeSlotRequest> requests);
}
