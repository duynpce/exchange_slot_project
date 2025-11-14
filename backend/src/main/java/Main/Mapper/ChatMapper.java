package Main.Mapper;

import Main.DTO.Chat.CreateChatDTO;
import Main.DTO.Chat.GetChatDTO;
import Main.Entity.Chat;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper{
    Chat toEntity(CreateChatDTO createChatDTO);
    List<GetChatDTO> toDtoList(List<Chat> chats);
}
