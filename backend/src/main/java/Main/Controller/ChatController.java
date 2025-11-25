package Main.Controller;

import Main.DTO.Chat.CreateChatDTO;
import Main.DTO.Chat.GetChatDTO;
import Main.DTO.Common.ResponseDTO;
import Main.Entity.Account;
import Main.Entity.Chat;
import Main.Exception.BaseException;
import Main.Mapper.ChatMapper;
import Main.Service.AccountService;
import Main.Service.ChatService;
import Main.Utility.JwtUtil;
import lombok.RequiredArgsConstructor;
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
    private final AccountService accountService;



    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@RequestBody CreateChatDTO createChatDTO){
        Chat chat = chatMapper.toEntity(createChatDTO);
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
            Account account = accountService.findByUserName(username);
            List<Chat> chats = chatService.findByUserId(account.getId(), page);
            List<GetChatDTO> data = chatMapper.toDtoList(chats);
            ResponseDTO<List<GetChatDTO>> response =
                    new ResponseDTO<>(true, "chats loaded successfully", "no error", data);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        throw new BaseException("have not logged in", HttpStatus.UNAUTHORIZED);
    }


}
