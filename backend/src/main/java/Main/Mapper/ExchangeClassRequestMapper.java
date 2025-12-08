package Main.Mapper;

import Main.DTO.ExchangeClassRequest.CreateExchangeClassRequestDTO;
import Main.DTO.ExchangeClassRequest.GetExchangeClassRequestDTO;
import Main.DTO.ExchangeClassRequest.UpdateExchangeClassRequestDTO;
import Main.Entity.ExchangeClassRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // spring --> implement as a bean
public interface ExchangeClassRequestMapper {

    ExchangeClassRequest toEntity(CreateExchangeClassRequestDTO dto);
    ExchangeClassRequest toEntity(UpdateExchangeClassRequestDTO dto);
    GetExchangeClassRequestDTO toDto(ExchangeClassRequest request);
    List<GetExchangeClassRequestDTO> toDtoList(List<ExchangeClassRequest> requests);
}
