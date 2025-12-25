package main.validator;

import main.entity.MajorClass;
import main.service.MajorClassService;
import main.utility.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MajorClassValidator {


    private final Util util;
    private final MajorClassService majorClassService;

    public void validateAddRequest(MajorClass majorClass){
        final String classCode = majorClass.getClassCode();

        util.throwExceptionIfExists(majorClassService.existsByClassCode(classCode)
                ,"existed class with class code: " +classCode);
    }

    public void validateUpdateRequest(MajorClass majorClass){
        final String classCode = majorClass.getClassCode();
        final String slot = majorClass.getSlot();
        MajorClass existingMajorClass = majorClassService.findByClassCode(classCode);

        util.throwExceptionIfNull(existingMajorClass,"no existing class with class code: " +classCode);

        majorClass.setId(existingMajorClass.getId());

    }
}
