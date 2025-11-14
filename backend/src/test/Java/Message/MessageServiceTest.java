package Message;

import Main.DTO.Message.SendMessageDTO;
import Main.Entity.Message;
import Main.Exception.BaseException;
import Main.Mapper.MessageMapper;
import Main.Repository.MessageRepository;
import Main.Service.MessageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    MessageRepository repository;

    @Mock
    MessageMapper mapper;

    @InjectMocks
    MessageService service;

    MessageServiceTestUtil util = new MessageServiceTestUtil();

    @AfterEach
    public void tearDown() {
        reset(repository, mapper);
    }

    @Test
    public void testSave() {
        System.out.println("Running testSave...");
        List<SendMessageDTO> dtos = util.getSendMessageDTOs();
        List<Message> expectedMessages = util.getTestMessages();
        for (int i = 0; i < dtos.size(); i++) {
            SendMessageDTO dto = dtos.get(i);
            Message expected = expectedMessages.get(i);

            when(mapper.toEntity(dto)).thenReturn(expected);

            Message input = mapper.toEntity(dto);
            when(repository.save(input)).thenReturn(input);

            Message result = service.save(input);

            assertEquals( expected, result, "#testCase " + (i + 1) + " failed: ID");
            verify(mapper, times(1)).toEntity(dto);
            verify(repository, times(1)).save(input);
            System.out.println("#testCase " + (i + 1) + " passed: Message saved with ID " + result.getId());
        }
        System.out.println("save Message passed \n");
    }

    @Test
    public void testLoadMessageByChatId_Success() {
        System.out.println("Running testLoadMessageByChatId_Success...");
        List<Message> messages = util.getTestMessages();
        int chatId = 1;
        int page = 0;

        //eq(chatId) to match the specific chatId
        when(repository.findByChatIdOrderByIdDesc(eq(chatId), any(Pageable.class))).thenReturn(messages);

        List<Message> result = service.loadMessageByChatId(chatId, page);

        assertNotNull(result);
        assertEquals(messages.size(), result.size());
        verify(repository, times(1)).findByChatIdOrderByIdDesc(eq(chatId), any(Pageable.class));
        System.out.println("testLoadMessageByChatId_Success passed: Loaded " + result.size() + " messages\n");
    }

    @Test
    public void testLoadMessageByChatId_NotFound() {
        System.out.println("Running testLoadMessageByChatId_NotFound...");
        int chatId = 2;
        int page = 0;

        when(repository.findByChatIdOrderByIdDesc(eq(chatId), any(Pageable.class))).thenReturn(new ArrayList<>());

        assertThrows(BaseException.class, () -> service.loadMessageByChatId(chatId, page));
        verify(repository, times(1)).findByChatIdOrderByIdDesc(eq(chatId), any(Pageable.class));
        System.out.println("testLoadMessageByChatId_NotFound passed: BaseException thrown as expected\n");
    }
}
