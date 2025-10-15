package Main.Validator;

import Main.DTO.CreateExchangeSlotRequestDTO;
import Main.Exception.ExchangeSlotRequestException;
import Main.Model.Enity.Account;
import Main.Model.Enity.ExchangeSlotRequest;
import Main.Model.Enity.MajorClass;
import Main.Service.AccountService;
import Main.Service.ExchangeSlotRequestService;
import Main.Service.MajorClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExchangeSlotRequestValidator {

    @Autowired
    MajorClassService majorClassService;

    @Autowired
    AccountService accountService;

    @Autowired
    ExchangeSlotRequestService exchangeSlotRequestService;

    private void throwExceptionIfNull(Object field, String message){
        if(field == null){
            throw new ExchangeSlotRequestException(message, HttpStatus.BAD_REQUEST);
        }
    }

    private void throwExceptionIfExists(boolean isExists, String message){
        if(isExists){
            throw new ExchangeSlotRequestException(message, HttpStatus.CONFLICT);
        }
    }

    private void throwExceptionIfNotExists(boolean isExists, String message){
        if(!isExists){
            throw new ExchangeSlotRequestException(message, HttpStatus.NOT_FOUND);
        }
    }

    public ExchangeSlotRequest validateAddRequest(CreateExchangeSlotRequestDTO request){
        final String studentCode  = request.getStudentCode();
        final String desiredSlot = request.getDesiredSlot();

        throwExceptionIfExists(exchangeSlotRequestService.existsByStudentCode(studentCode), "existed request with student code: " + studentCode );

        Account account = accountService.findByStudentCode(studentCode);
        throwExceptionIfNull(account, "no account with student code: " + studentCode);

        final String currentClassCode = account.getMajorClass().getClassCode();
        MajorClass currentClass = majorClassService.findByClassCode(currentClassCode);
        throwExceptionIfNull(currentClass, "no existing class with class code: " + currentClassCode);

        String currentSlot = currentClass.getSlot();

        if(currentSlot.equals(desiredSlot)){
            throw new ExchangeSlotRequestException
                    ("cannot make request for the same slot changes: " + currentSlot, HttpStatus.NOT_FOUND);
        }

        return new ExchangeSlotRequest(studentCode,currentClassCode, desiredSlot ,currentSlot);

    }

}
