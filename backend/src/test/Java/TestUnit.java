

import Main.Exchange_Student_Project;
import Main.Test.RunTestcase;
import Main.Test.UtilityForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;




@SpringBootTest(classes = Exchange_Student_Project.class)
class TestUnit {

    @Autowired
    RunTestcase run;

    @Autowired
    UtilityForTesting Test;

	@Test
	void contextLoads() {
//        Test.generateClassRequest(30);
//        Test.generateSlotRequest(30);
//        Test.generateAccount(30);
//        run.testAddAccount();
//        run.testAddClassRequest();
        run.testAddSlotRequest();

	}

}
