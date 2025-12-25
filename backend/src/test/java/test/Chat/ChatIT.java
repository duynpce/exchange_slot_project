package test.Chat;

import test.Account.AccountServiceTestUtil;
import test.IntegrationTest;
import main.dto.Chat.CreateChatDTO;
import main.entity.Account;
import main.entity.Chat;
import main.entity.MajorClass;
import main.exception.BaseException;
import main.mapper.ChatMapper;
import main.service.AccountService;
import main.service.ChatService;
import main.service.MajorClassService;
import main.utility.JwtUtil;
import main.validator.ChatValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ChatIT extends IntegrationTest {

    @Autowired
    ChatService chatService;

    @Autowired
    ChatValidator chatValidator;

    @Autowired
    AccountService accountService;

    @Autowired
    MajorClassService majorClassService;

    @MockitoBean
    JwtUtil jwtUtil;

    AccountServiceTestUtil accountServiceTestUtil = new AccountServiceTestUtil();

    @Test
    public void testAdd_Success() {
        // Setup: create two accounts
        List<Account> accounts = accountServiceTestUtil.getTestCase();
        Account account1 = accounts.get(0);
        Account account2 = accounts.get(1);

        majorClassService.save(new MajorClass(0, account1.getClassCode(), "1,2"));
        majorClassService.save(new MajorClass(0, account2.getClassCode(), "3,4"));
        Account savedAccount1 = accountService.save(account1);
        Account savedAccount2 = accountService.save(account2);


        Chat chat = new Chat();
        chat.setUserId1(savedAccount1.getId());
        chat.setUserId2(savedAccount2.getId());

        chatValidator.validateAddRequest(chat);
        Chat saved = chatService.add(chat);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0, "ID must be greater than 0");
        assertEquals(savedAccount1.getId(), saved.getUserId1(), "User1 IDs mismatch");
        assertEquals(savedAccount2.getId(), saved.getUserId2(), "User2 IDs mismatch");
    }

    @Test
    public void testAdd_SameUser() {
        // Setup: create one account
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        Account savedAccount = accountService.save(account);

        // Try to create chat with same user
        Chat chat = new Chat();
        chat.setUserId1(savedAccount.getId());
        chat.setUserId2(savedAccount.getId());

        BaseException exception = assertThrows(BaseException.class, () -> {
            chatValidator.validateAddRequest(chat);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus(), "HTTP status should be BAD_REQUEST");
    }

    @Test
    public void testLoadChats_Success() {
        // Setup: create accounts and chat
        List<Account> accounts = accountServiceTestUtil.getTestCase();
        Account account1 = accounts.get(0);
        Account account2 = accounts.get(1);

        majorClassService.save(new MajorClass(0, account1.getClassCode(), "1,2"));
        majorClassService.save(new MajorClass(0, account2.getClassCode(), "3,4"));
        Account savedAccount1 = accountService.save(account1);
        Account savedAccount2 = accountService.save(account2);

        Chat chat = new Chat();
        chat.setUserId1(savedAccount1.getId());
        chat.setUserId2(savedAccount2.getId());

        chatValidator.validateAddRequest(chat);
        chatService.add(chat);

        // Mock JWT to return account1's username
        when(jwtUtil.getUsername()).thenReturn(account1.getUsername());

        List<Chat> results = chatService.findByUserId(savedAccount1.getId(), 0);

        assertNotNull(results);
        assertFalse(results.isEmpty(), "Results should not be empty");
        assertEquals(savedAccount1.getId(), results.getFirst().getUserId1(), "User1 ID mismatch");
    }

    @Test
    public void testLoadChats_NoChats() {
        // Setup: create account without any chats
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        Account savedAccount = accountService.save(account);

        // Try to load chats for user with no chats
        BaseException exception = assertThrows(BaseException.class, () -> {
            chatService.findByUserId(savedAccount.getId(), 0);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
        assertTrue(exception.getMessage().contains("no chat with user id"), "Error message mismatch");
    }
}

