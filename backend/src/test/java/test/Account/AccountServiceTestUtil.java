package test.Account;

import main.entity.Account;
import main.constant.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountServiceTestUtil {


    Role user = Role.USER;

    public List<Account> getTestCase (){
        List<Account> testCase  = new ArrayList<>();
         for(int i = 1; i <= 3;i++){
             Account account = new Account
                     ("user" + i,"aPassword123@" + i,
                             "phoneNumber" + i,
                             "email" + i + "@gmail.com",
                             "accountName" + i,
                             "studentCode" + i,"classCode" + i,user);
             testCase.add(account);

         }

        return testCase;
    }




}
