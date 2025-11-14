package Main.Controller;

import Main.DTO.Common.ResponseDTO;
import Main.DTO.Message.ResponseMessageDTO;
import Main.DTO.Message.SendMessageDTO;
import Main.Entity.Message;
import Main.Mapper.MessageMapper;
import Main.Service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message/")
public class MessageController {
    private final SimpMessageSendingOperations messagingTemplate;

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    // WebSocket endpoint to send messages /app/sendMessage
    @MessageMapping("sendMessage")
    public void sendMessage(@Payload SendMessageDTO sendMessageDTO){
        // Send the message to the specific chat topic, similar to @SendTo("/topic/{chatId}")
        int chatId = sendMessageDTO.getChatId();
        messagingTemplate.convertAndSend("/topic/" + chatId, sendMessageDTO);

        // Save the message to the database
        Message message = messageMapper.toEntity(sendMessageDTO);
        messageService.save(message);
    }

    @GetMapping("id/{id}/page/{page}")
    public ResponseEntity<ResponseDTO<List<ResponseMessageDTO>>> loadMessages(@PathVariable int id, @PathVariable int page){
        List<ResponseMessageDTO> messages = messageMapper.toDtoList(messageService.loadMessageByChatId(id, page));

        ResponseDTO<List<ResponseMessageDTO>> response =
                new ResponseDTO<>(true, "messages loaded successfully", "no error", messages);
        return ResponseEntity.ok(response);
    }
}
