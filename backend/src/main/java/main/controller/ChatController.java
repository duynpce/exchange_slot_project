package main.controller;

import main.dto.Chat.CreateChatDTO;
import main.dto.Chat.GetChatDTO;
import main.dto.Common.ResponseDTO;
import main.entity.Account;
import main.entity.Chat;
import main.exception.BaseException;
import main.mapper.ChatMapper;
import main.service.AccountService;
import main.service.ChatService;
import main.utility.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main.validator.ChatValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final JwtUtil jwtUtil;
    private final ChatMapper chatMapper;
    private final ChatService chatService;
    private final ChatValidator chatValidator;
    private final AccountService accountService;



    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@Valid @RequestBody CreateChatDTO createChatDTO){
        Chat chat = chatMapper.toEntity(createChatDTO);
        chatValidator.validateAddRequest(chat);
        chatService.add(chat);

        ResponseDTO<String> response =
                new ResponseDTO<>(true, "chat created successfully", "no error", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<ResponseDTO<List<GetChatDTO>>> LoadChatsByByContextHolder(@PathVariable int page){

        if(page < 0){
            throw new BaseException("page must >= 0", HttpStatus.BAD_REQUEST);
        }

        final String username = jwtUtil.getUsername();

        if(username != null){
            Account account = accountService.findByUsername(username);
            List<Chat> chats = chatService.findByUserId(account.getId(), page);
            List<GetChatDTO> data = chatMapper.toDtoList(chats);
            ResponseDTO<List<GetChatDTO>> response =
                    new ResponseDTO<>(true, "chats loaded successfully", "no error", data);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        throw new BaseException("have not logged in", HttpStatus.UNAUTHORIZED);
    }


}
