package Main.Mapper;

import Main.DTO.ExchangeSlotRequest.CreateExchangeSlotRequestDTO;
import Main.DTO.ExchangeSlotRequest.GetExchangeSlotRequestDTO;
import Main.Entity.ExchangeSlotRequest;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ExchangeSlotRequestMapper {
    ExchangeSlotRequest toEntity(CreateExchangeSlotRequestDTO dto);
    GetExchangeSlotRequestDTO toDto(ExchangeSlotRequest request);
    List<GetExchangeSlotRequestDTO> toDtoList(List<ExchangeSlotRequest> requests);
}
