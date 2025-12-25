package test.MajorClass;

import main.entity.MajorClass;

import java.util.ArrayList;
import java.util.List;

public class MajorClassServiceTestUtil {

    public List<MajorClass> getTestCase() {
        List<MajorClass> testCases = new ArrayList<>();

        testCases.add(new MajorClass(0, "SE1801", "1,2"));
        testCases.add(new MajorClass(0, "SE1802", "3,4"));
        testCases.add(new MajorClass(0, "SE1803", "3,4"));
        testCases.add(new MajorClass(0, "AI1801", "1,2"));
        testCases.add(new MajorClass(0, "AI1802", "3,4"));

        return testCases;
    }
}
