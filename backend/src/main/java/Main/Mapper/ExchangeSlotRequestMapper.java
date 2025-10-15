package Main.Mapper;

import Main.DTO.CreateExchangeSlotRequestDTO;
import Main.Model.Enity.ExchangeSlotRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExchangeSlotRequestMapper {

    CreateExchangeSlotRequestDTO toDto(ExchangeSlotRequest request);
    List<CreateExchangeSlotRequestDTO> toDtoList(List<ExchangeSlotRequest> requests);
}
