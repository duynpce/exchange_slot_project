package Main.Validator;

import Main.Entity.MajorClass;
import Main.Service.MajorClassService;
import Main.Utility.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MajorClassValidator {

    @Autowired
    util utility;

    @Autowired
    MajorClassService majorClassService;

    public void validateAddRequest(MajorClass majorClass){
        final String classCode = majorClass.getClassCode();

        utility.throwExceptionIfNull(classCode, "null class code");
        utility.throwExceptionIfNull(majorClass.getSlot(), "null slot");

        utility.throwExceptionIfExists(majorClassService.existsByClassCode(classCode)
                ,"existed class with class code: " +classCode);
    }

    public void validateUpdateRequest(MajorClass majorClass){
        final String classCode = majorClass.getClassCode();

        utility.throwExceptionIfNull(classCode, "null class code");
        utility.throwExceptionIfNull(majorClass.getSlot(), "null slot");

        utility.throwExceptionIfNotExists(majorClassService.existsByClassCode(classCode),
                "not found class with class code: " + classCode);

    }
}
