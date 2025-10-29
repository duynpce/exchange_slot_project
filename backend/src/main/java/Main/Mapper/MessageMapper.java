package Main.Mapper;

import Main.DTO.Message.SendMessageDTO;
import org.mapstruct.Mapper;
import Main.Entity.Message;
@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toEntity(SendMessageDTO sendMessageDTO);
}
