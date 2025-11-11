package ExchangeClassRequest;

import Main.Entity.ExchangeClassRequest;

import java.util.ArrayList;
import java.util.List;

public class ExchangeClassRequestServiceTestUtil {

    public List<ExchangeClassRequest> getTestCase() {
        List<ExchangeClassRequest> testCase = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            ExchangeClassRequest request = new ExchangeClassRequest(// ID is assigned directly since it's likely auto-generated in DB
                     "studentCode" + i,
                    "desiredClassCode" + i,
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
