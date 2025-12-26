package main.service;

import main.entity.Chat;
import main.constant.IntConstant;
import main.exception.BaseException;
import main.repository.ChatRepository;
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
public class ChatService {

    private static final String CACHE_NAME = "chatData";
    private final ChatRepository chatRepository;

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public Chat add(Chat chat) {
        return chatRepository.save(chat);
    }

    @Cacheable(value = CACHE_NAME, key = "{#userId,#page}")
    public List<Chat> findByUserId(int userId, int page) {
        Pageable pageable = PageRequest.of(page, IntConstant.DEFAULT_PAGE_SIZE.getValue());
        List<Chat> data = chatRepository.findByUserId(userId, pageable);

        if(data.isEmpty()) {
            throw new BaseException("no chat with user id " + userId + " found", HttpStatus.NOT_FOUND);
        }

        return data;
    }

}
