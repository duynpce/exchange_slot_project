package Main.Validator;

import Main.DTO.Chat.CreateChatDTO;
import Main.Service.AccountService;
import Main.Service.ChatService;
import Main.Utility.util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatValidator {
    private final ChatService chatService;
    private final AccountService accountService;

    private final util utility;

    public void validateAddRequest(CreateChatDTO dto) {
        int user1Id = dto.getUser1Id();
        int user2Id = dto.getUser2Id();
        utility.throwExceptionIfNotExists(accountService.existsById(user1Id), "no account with id: " + user1Id);
        utility.throwExceptionIfNotExists(accountService.existsById(user2Id), "no account with id: " + user2Id);

    }

}
