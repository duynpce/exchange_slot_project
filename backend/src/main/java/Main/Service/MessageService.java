package Main.Service;

import Main.Enum.IntConstant;
import Main.Exception.BaseException;
import Main.Entity.Message;
import Main.Repository.MessageRepository;

import Main.Utility.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    private final int pageSize = IntConstant.DEFAULT_PAGE_SIZE.getValue();
    private final String cacheData = "messageData";

    private final MessageRepository messageRepository;


    @CacheEvict(value = cacheData, allEntries = true)
    public Message save(Message message){

        return messageRepository.save(message);
    }

    @Cacheable(value = cacheData, key = "{#chatId, #page}")
    public List<Message> loadMessageByChatId(int chatId, int page){
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Message> data = messageRepository.findByChatIdOrderByIdDesc(chatId, pageable);

        if(data.isEmpty()) {
            throw new BaseException("no message with chat id " + chatId + " found", HttpStatus.NOT_FOUND);
        }

        return data;
    }
}
