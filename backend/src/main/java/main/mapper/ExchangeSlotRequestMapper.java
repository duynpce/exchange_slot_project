package main.mapper;

import main.dto.ExchangeSlotRequest.CreateExchangeSlotRequestDTO;
import main.dto.ExchangeSlotRequest.GetExchangeSlotRequestDTO;
import main.entity.ExchangeSlotRequest;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ExchangeSlotRequestMapper {
    ExchangeSlotRequest toEntity(CreateExchangeSlotRequestDTO dto);
    GetExchangeSlotRequestDTO toDto(ExchangeSlotRequest request);
    List<GetExchangeSlotRequestDTO> toDtoList(List<ExchangeSlotRequest> requests);
}
