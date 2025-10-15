package Main.Validator;

import Main.DTO.CreateExchangeClassRequestDTO;
import Main.Exception.ExchangeClassRequestException;
import Main.Model.Enity.Account;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Model.Enity.MajorClass;
import Main.Service.AccountService;
import Main.Service.ExchangeClassRequestService;
import Main.Service.MajorClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExchangeClassRequestValidator {

    @Autowired
    MajorClassService majorClassService;

    @Autowired
    AccountService accountService;

    @Autowired
    ExchangeClassRequestService exchangeClassRequestService;

    private void throwExceptionIfNull(Object field, String message){
        if(field == null){
            throw new ExchangeClassRequestException(message, HttpStatus.BAD_REQUEST);
        }
    }

    private void throwExceptionIfExists(boolean isExists, String message){
        if(isExists){
            throw new ExchangeClassRequestException(message, HttpStatus.CONFLICT);
        }
    }

    private void throwExceptionIfNotExists(boolean isExists, String message){
        if(!isExists){
            throw new ExchangeClassRequestException(message, HttpStatus.NOT_FOUND);
        }
    }


    public ExchangeClassRequest validateAddRequest(CreateExchangeClassRequestDTO request){
        final String studentCode  = request.getStudentCode();
        final String desiredClassCode = request.getDesiredClassCode();

        throwExceptionIfExists(exchangeClassRequestService.existsByStudentCode(studentCode), "existed request with student code: " + studentCode );

        Account account = accountService.findByStudentCode(studentCode);
        throwExceptionIfNull(account, "no account with student code: " + studentCode);

        final String currentClassCode = account.getMajorClass().getClassCode();
        MajorClass currentClass = majorClassService.findByClassCode(currentClassCode);
        MajorClass desiredClass = majorClassService.findByClassCode(desiredClassCode);
        throwExceptionIfNull(currentClass, "no existing class with class code: " + currentClassCode);
        throwExceptionIfNull(desiredClass, "no existing class with class code: " + desiredClassCode);

        String currentSlot = currentClass.getSlot();
        String desiredSlot = desiredClass.getSlot();

        if(currentSlot.equals(desiredSlot)){
            throw new ExchangeClassRequestException
                    ("cannot make request for the same slot changes: " + currentSlot, HttpStatus.NOT_FOUND);
        }

        return new ExchangeClassRequest(studentCode, desiredClassCode,currentClassCode, desiredSlot ,currentSlot);

    }
}
