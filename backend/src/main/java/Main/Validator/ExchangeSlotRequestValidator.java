package Main.Validator;

import Main.DTO.CreateExchangeSlotRequestDTO;
import Main.Exception.ExchangeClassRequestException;
import Main.Exception.ExchangeSlotRequestException;
import Main.Model.Enity.Account;
import Main.Model.Enity.ExchangeSlotRequest;
import Main.Model.Enity.MajorClass;
import Main.Service.AccountService;
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

    public ExchangeSlotRequest validateAddRequest(CreateExchangeSlotRequestDTO request){
        final String studentCode  = request.getStudentCode();

        Account account = accountService.findByStudentCode(studentCode);
        if(account == null){ //is empty
            throw new ExchangeClassRequestException
                    ("no existing student with student code: " + studentCode, HttpStatus.NOT_FOUND);
        }

        final String currentClassCode = account.getMajorClass().getClassCode();
        Optional<MajorClass> currentClass = majorClassService.findByClassCode(currentClassCode);
        boolean existsStudentCode = accountService.existsByStudentCode(studentCode);

        if(currentClass.isEmpty()) {
            throw new ExchangeSlotRequestException
                    ("no existing class with class code: " + currentClassCode, HttpStatus.NOT_FOUND);
        }

        if(!existsStudentCode){
            throw new ExchangeSlotRequestException
                    ("no existing student with student code: " + studentCode, HttpStatus.NOT_FOUND);
        }

        String currentSlot = currentClass.get().getSlot();
        String desiredSlot = request.getDesiredSlot();

        if(currentSlot.equals(desiredSlot)){
            throw new ExchangeSlotRequestException
                    ("cannot make request for the same slot changes: " + currentSlot, HttpStatus.NOT_FOUND);
        }

        return new ExchangeSlotRequest(studentCode,currentClassCode,desiredSlot, currentSlot);

    }
}
