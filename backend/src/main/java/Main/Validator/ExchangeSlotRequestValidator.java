package Main.Validator;

import Main.Exception.BaseException;
import Main.Entity.Account;
import Main.Entity.ExchangeSlotRequest;
import Main.Entity.MajorClass;
import Main.Service.AccountService;
import Main.Service.ExchangeSlotRequestService;
import Main.Service.MajorClassService;

import Main.Utility.util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeSlotRequestValidator {


    private  final MajorClassService majorClassService;
    private  final AccountService accountService;
    private final ExchangeSlotRequestService exchangeSlotRequestService;

    private final util utility;

    public void validateAddRequest(ExchangeSlotRequest request){
        final String studentCode  = request.getStudentCode();
        final String desiredSlot = request.getDesiredSlot();

        utility.throwExceptionIfExists(exchangeSlotRequestService.existsByStudentCode(studentCode), "existed request with student code: " + studentCode );

        Account account = accountService.findByStudentCode(studentCode);
        utility.throwExceptionIfNull(account, "no account with student code: " + studentCode);

        final String currentClassCode = account.getMajorClass().getClassCode();
        MajorClass currentClass = majorClassService.findByClassCode(currentClassCode);
        utility.throwExceptionIfNull(currentClass, "no existing class with class code: " + currentClassCode);

        String currentSlot = currentClass.getSlot();

        if(currentSlot.equals(desiredSlot)){
            throw new BaseException
                    ("cannot make request for the same slot changes: " + currentSlot, HttpStatus.NOT_FOUND);
        }

        /// valid request --> add necessary information to the request
        request.setCurrentSlot(currentSlot);
        request.setCurrentClassCode(currentClassCode);
        request.setDesiredSlot(desiredSlot);
    }
}
