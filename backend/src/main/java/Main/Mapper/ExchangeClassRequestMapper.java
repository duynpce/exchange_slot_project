package Main.Mapper;

import Main.DTO.ExchangeClassRequest.CreateExchangeClassRequestDTO;
import Main.DTO.ExchangeClassRequest.ExchangeClassRequestResponseDTO;
import Main.DTO.ExchangeClassRequest.UpdateExchangeClassRequestDTO;
import Main.Entity.ExchangeClassRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // spring --> implement as a bean
public interface ExchangeClassRequestMapper {

    ExchangeClassRequest toEntity(CreateExchangeClassRequestDTO dto);
    ExchangeClassRequest toEntity(UpdateExchangeClassRequestDTO dto);
    ExchangeClassRequestResponseDTO toDto(ExchangeClassRequest request);
    List<ExchangeClassRequestResponseDTO> toDtoList(List<ExchangeClassRequest> requests);
}
