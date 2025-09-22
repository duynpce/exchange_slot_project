package Main.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import java.util.Optional;

import Main.Model.Enity.Account;
import Main.Repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public boolean register(Account account){
        Optional<Account> checkUserName = accountRepository.findByUserName(account.getUserName());///error
        Optional<Account> checkPhoneNumber = accountRepository.findByPhoneNumber(account.getPhoneNumber());//error
        if(checkUserName.isPresent() || checkPhoneNumber.isPresent()) return false;


        accountRepository.save(account);
        return true;
    }

    public  boolean login(Account account){
        Optional<Account> optionalAccount = accountRepository.findByUserName(account.getUserName());
        if(optionalAccount.isPresent()){;
            Account FoundAccount = optionalAccount.get();
            return FoundAccount.getPassword().equals(account.getPassword());
        }
        return  false;
    }

    public boolean resetPassword(Account accountWithNewPassWord){
        return accountRepository.
                resetPassword(accountWithNewPassWord.getUserName(),accountWithNewPassWord.getPassword()) == 1;
    }




}
