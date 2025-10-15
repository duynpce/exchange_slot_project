package Main.Mapper;

import Main.DTO.CreateExchangeClassRequestDTO;
import Main.DTO.ExchangeClassRequestResponseDTO;
import Main.Model.Enity.ExchangeClassRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // spring --> implement as a bean
public interface ExchangeClassRequestMapper {

    ExchangeClassRequest toEntity(CreateExchangeClassRequestDTO dto);
    ExchangeClassRequestResponseDTO toDto(ExchangeClassRequest request);
    List<ExchangeClassRequestResponseDTO> toDtoList(List<ExchangeClassRequest> requests);
}
