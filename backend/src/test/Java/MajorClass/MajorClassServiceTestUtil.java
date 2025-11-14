package MajorClass;

import Main.Entity.MajorClass;

import java.util.ArrayList;
import java.util.List;

public class MajorClassServiceTestUtil {

    public List<MajorClass> getTestCase() {
        List<MajorClass> testCase = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            MajorClass majorClass = new MajorClass();
            majorClass.setClassCode("classCode" + i);
            majorClass.setSlot("slot" + i);
            majorClass.setId(i);
            testCase.add(majorClass);
        }

        return testCase;
    }
}
