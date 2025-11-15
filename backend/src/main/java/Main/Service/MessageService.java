package Main.Service;

import Main.DTO.Message.SendMessageDTO;
import Main.Enum.Constant;
import Main.Exception.BaseException;
import Main.Mapper.MessageMapper;
import Main.Entity.Message;
import Main.Repository.MessageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
    private final int PageSize = Constant.DefaultPageSize.getPageSize();

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public Message save(Message message){
        return messageRepository.save(message);
    }

    @Cacheable(value = "messageData", key = "#chatId" + '-' + "#page")
    public List<Message> loadMessageByChatId(int chatId, int page){
        Pageable pageable = PageRequest.of(page, PageSize);
        List<Message> data = messageRepository.findByChatIdOrderByIdDesc(chatId, pageable);

        if(data.isEmpty()) {
            throw new BaseException("no message with chat id " + chatId + " found", HttpStatus.NOT_FOUND);
        }

        return data;
    }
}
