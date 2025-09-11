package Main.RestController;

import Main.DTO.LoginDTO;
import Main.Model.Enity.Account;
import Main.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import Main.Utility.Utility;

@RestController
@RequestMapping("/account")
public class AccountRestController {
    @Autowired
    AccountService accountService;

    @Autowired
   Utility utility;



    @GetMapping("/login")
    public LoginDTO login(Account account) {


        boolean loginSuccess = accountService.login(account);

        if(loginSuccess){
            return new LoginDTO(utility.getRefreshToken(account.getUserName()), utility.getAccessToken(account.getUserName()),"login success");
        }

        return  new LoginDTO("no refresh token", "no access token", "login failed");
    }

    @GetMapping("/register")
    public String register(Account account){
        boolean registerSuccess = accountService.register(account);

        if(!registerSuccess) return "register failed";

        return "register successfully";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(Account account, String newPassword){/// account ID is temp

        boolean loginSuccess = accountService.resetPassword(account,newPassword);

        if(loginSuccess){
            return "reset successfully";
        }

        return "reset failed";

    }


}
