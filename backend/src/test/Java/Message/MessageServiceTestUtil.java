package Message;

import Main.DTO.Message.SendMessageDTO;
import Main.Entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceTestUtil {

    public List<SendMessageDTO> getSendMessageDTOs() {
        List<SendMessageDTO> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            SendMessageDTO dto = new SendMessageDTO(i, i + 10, "msg " + i);
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
