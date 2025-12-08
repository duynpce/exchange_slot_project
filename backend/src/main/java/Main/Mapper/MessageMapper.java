package Main.Mapper;

import Main.DTO.Message.GetMessageDTO;
import org.mapstruct.Mapper;
import Main.Entity.Message;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toEntity(GetMessageDTO getMessageDTO);
    GetMessageDTO toDto(Message message);
    List<GetMessageDTO> toDtoList(List<Message> messages);
}
