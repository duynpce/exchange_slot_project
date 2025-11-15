package Chat;

import Main.Entity.Chat;
import Main.Exception.BaseException;
import Main.Repository.ChatRepository;
import Main.Service.ChatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    ChatRepository repository;

    @InjectMocks
    ChatService service;

    ChatServiceTestUtil util = new ChatServiceTestUtil();

    @AfterEach
    public void tearDown() {
        reset(repository);
    }

    @Test
    public void testAdd() {
        System.out.println("Running testAdd...");
        List<Chat> testCases = util.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Chat input = testCases.get(i);
            // set expected saved id (adjust if Chat uses Long)

            when(repository.save(input)).thenReturn(input);

            Chat result = service.add(input);

            assertEquals(input.getId(), result.getId(), "#testCase " + (i + 1) + " failed: ID");
            verify(repository, times(1)).save(input);
            System.out.println("#testCase " + (i + 1) + " passed: Chat added with ID " + result.getId());
            System.out.println("userId1: " + result.getUserId1() + ", userId2: " + result.getUserId2());
        }
        System.out.println("add Chat passed \n");
    }

    @Test
    public void testFindByUserIdFound() {
        System.out.println("Running testFindByUserIdFound...");
        List<Chat> testCases = util.getTestCase();
        int userId = 1;
        when(repository.findByUserId(eq(userId), any(Pageable.class))).thenReturn(testCases);

        List<Chat> result = service.findByUserId(userId, 0);

        assertEquals(testCases, result, "#testCase: Found chats do not match expected");
        verify(repository, times(1)).findByUserId(eq(userId), any(Pageable.class));
        System.out.println("#testCase passed: Found " + result.size() + " chats for userId " + userId + "\n");
    }

    @Test
    public void testFindByUserIdNotFound() {
        System.out.println("Running testFindByUserIdNotFound...");
        int userId = 999;
        when(repository.findByUserId(eq(userId), any(Pageable.class))).thenReturn(Collections.emptyList());

        BaseException ex = assertThrows(BaseException.class, () -> service.findByUserId(userId, 0));
        assertTrue(ex.getMessage().contains("no chat with user id " + userId));
        verify(repository, times(1)).findByUserId(eq(userId), any(Pageable.class));
        System.out.println("#testCase passed: No chats found for userId " + userId + "\n");
    }
}
