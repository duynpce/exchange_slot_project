package Main.Validator;

import Main.DTO.Account.UpdateAccountDTO;
import Main.Entity.Account;
import Main.Exception.BaseException;
import Main.Service.MajorClassService;
import Main.Utility.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountValidator {
    private final MajorClassService majorClassService;;
    private final Util util;
    //validate and set new information for  account
    public void validateUpdateAccount(UpdateAccountDTO updateAccountDTO, Account account){
        final String newStudentCode = updateAccountDTO.getStudentCode();
        final String newClassCode = updateAccountDTO.getClassCode();

        if(newStudentCode  == null && newClassCode == null){
            throw new BaseException("null both new student code and new class code", HttpStatus.BAD_REQUEST);
        }

        if(newClassCode != null){// if not null --> set new
            account.setClassCode(newClassCode);
            util.throwExceptionIfNotExists(majorClassService.existsByClassCode(newClassCode),"no class with class code: " +newClassCode );
        }

        if(newStudentCode != null){
            account.setStudentCode(newStudentCode);
        }


    }
}
