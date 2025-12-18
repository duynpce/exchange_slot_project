package Main.Validator;

import Main.Exception.BaseException;
import Main.Entity.Account;
import Main.Entity.ExchangeClassRequest;
import Main.Entity.MajorClass;
import Main.Service.AccountService;
import Main.Service.ExchangeClassRequestService;
import Main.Service.MajorClassService;


import Main.Utility.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeClassRequestValidator {

    private final MajorClassService majorClassService;
    private final AccountService accountService;
    private final ExchangeClassRequestService exchangeClassRequestService;
    private final Util util;

    public void validateAddRequest(ExchangeClassRequest request){
        final String studentCode  = request.getStudentCode();
        final String desiredClassCode = request.getDesiredClassCode();

        util.throwExceptionIfExists(exchangeClassRequestService.existsByStudentCode(studentCode)
                , "existed request with student code: " + studentCode );

        Account account = accountService.findByStudentCode(studentCode);
        util.throwExceptionIfNull(account, "no account with student code: " + studentCode);

        final String currentClassCode = account.getMajorClass().getClassCode();
        MajorClass currentClass = majorClassService.findByClassCode(currentClassCode);
        MajorClass desiredClass = majorClassService.findByClassCode(desiredClassCode);
        util.throwExceptionIfNull(currentClass, "no existing class with class code: " + currentClassCode);
        util.throwExceptionIfNull(desiredClass, "no existing class with class code: " + desiredClassCode);

        String currentSlot = currentClass.getSlot();
        String desiredSlot = desiredClass.getSlot();
        if(currentSlot.equals(desiredSlot)){
            throw new BaseException("cannot make request for the same slot changes: " + currentSlot, HttpStatus.NOT_FOUND);
        }

        /// valid request --> add necessary information to the request
        request.setCurrentClassCode(currentClassCode);
        request.setCurrentSlot(currentSlot);
        request.setDesiredSlot(desiredSlot);
    }

    public void validateUpdateRequest(ExchangeClassRequest request) {
        final String studentCode  = request.getStudentCode();
        final String desiredClassCode = request.getDesiredClassCode();


        Account account = accountService.findByStudentCode(studentCode);
        util.throwExceptionIfNull(account, "no account with student code: " + studentCode);

        final String currentClassCode = account.getMajorClass().getClassCode();
        MajorClass currentClass = majorClassService.findByClassCode(currentClassCode);
        MajorClass desiredClass = majorClassService.findByClassCode(desiredClassCode);
        util.throwExceptionIfNull(currentClass, "no existing class with class code: " + currentClassCode);
        util.throwExceptionIfNull(desiredClass, "no existing class with class code: " + desiredClassCode);

        String currentSlot = currentClass.getSlot();
        String desiredSlot = desiredClass.getSlot();

        if (currentSlot.equals(desiredSlot)) {
            throw new BaseException("cannot make request for the same slot changes: " + currentSlot, HttpStatus.BAD_REQUEST);
        }

        /// valid request --> add necessary information to the request
        request.setCurrentClassCode(currentClassCode);
        request.setCurrentSlot(currentSlot);
        request.setDesiredSlot(desiredSlot);
    }

}