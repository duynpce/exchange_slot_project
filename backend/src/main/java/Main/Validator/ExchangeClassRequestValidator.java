package Main.Validator;

import Main.DTO.ExchangeClassRequestDTO;
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


    public ExchangeClassRequest validateAddRequest(ExchangeClassRequestDTO request){
        final String studentCode  = request.getStudentCode();
        final String desiredClassCode = request.getDesiredClassCode();

         Account account = accountService.findByStudentCode(studentCode);
        if(account == null){// is empty
            throw new ExchangeClassRequestException
                    ("no existing student with student code: " + studentCode, HttpStatus.NOT_FOUND);
        }

        final String currentClassCode = account.getMajorClass().getClassCode();
        Optional<MajorClass> currentClass = majorClassService.findByClassCode(currentClassCode);
        Optional<MajorClass> desiredClass = majorClassService.findByClassCode(desiredClassCode);

        if(currentClass.isEmpty()) {
            throw new ExchangeClassRequestException
                    ("no existing class with class code: " + currentClassCode, HttpStatus.NOT_FOUND);
        }

        if(desiredClass.isEmpty()){
            throw new ExchangeClassRequestException
                    ("no existing class with class code: " + desiredClassCode, HttpStatus.NOT_FOUND);
        }

        String currentSlot = currentClass.get().getSlot();
        String desiredSlot = desiredClass.get().getSlot();

        if(currentSlot.equals(desiredSlot)){
            throw new ExchangeClassRequestException
                    ("cannot make request for the same slot changes: " + currentSlot, HttpStatus.NOT_FOUND);
        }

        return new ExchangeClassRequest(studentCode, desiredClassCode,currentClassCode, desiredSlot ,currentSlot);

    }
}
