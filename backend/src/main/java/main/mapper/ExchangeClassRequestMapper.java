package main.mapper;

import main.dto.ExchangeClassRequest.CreateExchangeClassRequestDTO;
import main.dto.ExchangeClassRequest.GetExchangeClassRequestDTO;
import main.dto.ExchangeClassRequest.UpdateExchangeClassRequestDTO;
import main.entity.ExchangeClassRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // spring --> implement as a bean
public interface ExchangeClassRequestMapper {

    ExchangeClassRequest toEntity(CreateExchangeClassRequestDTO dto);
    ExchangeClassRequest toEntity(UpdateExchangeClassRequestDTO dto);
    GetExchangeClassRequestDTO toDto(ExchangeClassRequest request);
    List<GetExchangeClassRequestDTO> toDtoList(List<ExchangeClassRequest> requests);
}
