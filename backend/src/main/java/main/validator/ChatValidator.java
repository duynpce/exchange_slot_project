package main.validator;

import main.dto.Chat.CreateChatDTO;
import main.entity.Chat;
import main.exception.BaseException;
import main.service.AccountService;
import main.service.ChatService;
import main.utility.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatValidator {
    private final ChatService chatService;
    private final AccountService accountService;

    private final Util util;

    public void validateAddRequest(Chat chat) {
        int user1Id = chat.getUserId1();
        int user2Id = chat.getUserId2();

        util.throwExceptionIfNotExists(accountService.existsById(user1Id), "no account with id: " + user1Id);
        util.throwExceptionIfNotExists(accountService.existsById(user2Id), "no account with id: " + user2Id);
        util.throwExceptionIfEquals(user1Id, user2Id, "cannot create chat with the same user ids");
    }

}
