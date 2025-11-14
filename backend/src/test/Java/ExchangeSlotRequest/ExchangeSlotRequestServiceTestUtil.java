package ExchangeSlotRequest;

import Main.Entity.ExchangeSlotRequest;

import java.util.ArrayList;
import java.util.List;

public class ExchangeSlotRequestServiceTestUtil {

    public List<ExchangeSlotRequest> getTestCase() {
        List<ExchangeSlotRequest> testCase = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            ExchangeSlotRequest request = new ExchangeSlotRequest(
                    "studentCode" + i,
                    "currentClassCode" + i,
                    "desiredSlot" + i,
                    "currentSlot" + i
            );
            request.setId(i);
            testCase.add(request);
        }

        return testCase;
    }
}
