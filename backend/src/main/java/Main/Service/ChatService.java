package Main.Service;

import Main.Entity.Chat;
import Main.Enum.Constant;
import Main.Exception.BaseException;
import Main.Repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ChatService {
    private final int pageSize = Constant.DefaultPageSize.getPageSize();

    private final ChatRepository chatRepository;

    public Chat add(Chat chat) {
        return chatRepository.save(chat);
    }

    @Cacheable(value = "chatData", key = "#userId" + '-' + "#page")
    public List<Chat> findByUserId(int userId, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Chat> data = chatRepository.findByUserId(userId, pageable);

        if(data.isEmpty()) {
            throw new BaseException("no chat with user id " + userId + " found", HttpStatus.NOT_FOUND);
        }

        return data;
    }

}
