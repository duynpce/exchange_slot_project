package test.Message;

import main.dto.Message.CreateMessageDTO;
import main.dto.Message.GetMessageDTO;
import main.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceTestUtil {

    public List<CreateMessageDTO> getGetMessageDTOs() {
        List<CreateMessageDTO> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            CreateMessageDTO dto = new CreateMessageDTO(i, i + 10, i + 100,  "msg " + i);
            list.add(dto);
        }
        return list;
    }

    public List<Message> getTestMessages() {
        List<Message> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Message m = new Message();
            m.setId(i);
            m.setChatId(i);
            m.setContent("msg " + i);
            m.setSenderId(i + 10);
            list.add(m);
        }
        return list;
    }
}
