package Main.Validator;

import Main.Entity.MajorClass;
import Main.Service.MajorClassService;
import Main.Utility.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MajorClassValidator {


    private final Util util;
    private final MajorClassService majorClassService;

    public void validateAddRequest(MajorClass majorClass){
        final String classCode = majorClass.getClassCode();

        util.throwExceptionIfNull(classCode, "null class code");
        util.throwExceptionIfNull(majorClass.getSlot(), "null slot");

        util.throwExceptionIfExists(majorClassService.existsByClassCode(classCode)
                ,"existed class with class code: " +classCode);
    }

    public void validateUpdateRequest(MajorClass majorClass){
        final String classCode = majorClass.getClassCode();
        final String slot = majorClass.getSlot();
        MajorClass existingMajorClass = majorClassService.findByClassCode(classCode);

        util.throwExceptionIfNull(classCode, "null class code");
        util.throwExceptionIfNull(slot, "null slot");
        util.throwExceptionIfNull(existingMajorClass,"no existing class with class code: " +classCode);

        majorClass.setId(existingMajorClass.getId());

    }
}
