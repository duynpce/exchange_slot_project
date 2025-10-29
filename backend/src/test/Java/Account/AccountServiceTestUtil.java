package Account;

import Main.Entity.Account;
import Main.Enum.Role;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceTestUtil {


    Role user = Role.USER;

    public List<Account> getTestCase (){
        List<Account> testCase  = new ArrayList<>();
         for(int i = 1; i <= 5;i++){
             Account account = new Account
                     ("user" + i,"aPassword123@" + i,
                             "phoneNumber" + i,"accountName" + i,
                             "studentCode" + i,"classCode" + i,user);
             testCase.add(account);

         }

        return testCase;
    }




}
