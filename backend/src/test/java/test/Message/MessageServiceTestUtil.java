package test.Message;

import main.dto.Message.GetMessageDTO;
import main.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceTestUtil {

    public List<GetMessageDTO> getGetMessageDTOs() {
        List<GetMessageDTO> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            GetMessageDTO dto = new GetMessageDTO(i, i + 10, i + 100,  "msg " + i);
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
