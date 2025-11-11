package Main.Mapper;

import Main.DTO.ExchangeSlotRequest.CreateExchangeSlotRequestDTO;
import Main.DTO.ExchangeSlotRequest.ExchangeSlotRequestResponseDTO;
import Main.Entity.ExchangeSlotRequest;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ExchangeSlotRequestMapper {
    ExchangeSlotRequest toEntity(CreateExchangeSlotRequestDTO dto);
    ExchangeSlotRequestResponseDTO toDto(ExchangeSlotRequest request);
    List<ExchangeSlotRequestResponseDTO> toDtoList(List<ExchangeSlotRequest> requests);
}
