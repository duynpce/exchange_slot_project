package Chat;

import Main.Entity.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatServiceTestUtil {

    public List<Chat> getTestCase() {
        List<Chat> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Chat chat = new Chat();
            chat.setId(i);
            chat.setUserId1(i);
            chat.setUserId2(i + 10);
            list.add(chat);
        }
        return list;
    }
}
