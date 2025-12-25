package main.mapper;

import main.dto.Chat.CreateChatDTO;
import main.dto.Chat.GetChatDTO;
import main.entity.Chat;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper{
    Chat toEntity(CreateChatDTO createChatDTO);
    List<GetChatDTO> toDtoList(List<Chat> chats);
}
