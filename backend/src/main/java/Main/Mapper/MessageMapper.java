package Main.Mapper;

import Main.DTO.Message.ResponseMessageDTO;
import Main.DTO.Message.SendMessageDTO;
import org.mapstruct.Mapper;
import Main.Entity.Message;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toEntity(SendMessageDTO sendMessageDTO);
    ResponseMessageDTO toDto(Message message);
    List<ResponseMessageDTO> toDtoList(List<Message> messages);
}
