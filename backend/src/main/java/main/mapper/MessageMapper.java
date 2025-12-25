package main.mapper;

import main.dto.Message.GetMessageDTO;
import org.mapstruct.Mapper;
import main.entity.Message;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toEntity(GetMessageDTO getMessageDTO);
    GetMessageDTO toDto(Message message);
    List<GetMessageDTO> toDtoList(List<Message> messages);
}
