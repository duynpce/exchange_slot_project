package Main.Service;


import Main.Exception.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
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
        Optional<Account> alreadyExistedUserName = accountRepository.findByUserName(account.getUserName());///error
        Optional<Account> alreadyExistedPhoneNumber = accountRepository.findByPhoneNumber(account.getPhoneNumber());//error
        Optional<Account> alreadyExistedStudentCode = accountRepository.findByStudentCode(account.getStudentCode());
        Optional<Account> alreadyExistedAccountName = accountRepository.findByAccountName(account.getAccountName());


        if(alreadyExistedUserName.isPresent()){
            throw new AccountException("existed user name", HttpStatus.CONFLICT);
        }
        else if(alreadyExistedPhoneNumber.isPresent()){
            throw new AccountException("existed phone number", HttpStatus.CONFLICT);
        }
        else if(alreadyExistedStudentCode.isPresent()){
            throw new AccountException("existed student code", HttpStatus.CONFLICT);
        }
        else if(alreadyExistedAccountName.isPresent()){
            throw new AccountException("existed account name", HttpStatus.CONFLICT);
        }

        accountRepository.save(account);
        return true;
    }

    public  boolean login(Account account){
        Optional<Account> optionalAccount = accountRepository.findByUserName(account.getUserName());

        if(optionalAccount.isPresent()){;
            Account FoundAccount = optionalAccount.get();
            return FoundAccount.getPassword().equals(account.getPassword());
        }

            throw new AccountException("no username Existed", HttpStatus.NOT_FOUND);

    }

    public boolean resetPassword(Account accountWithNewPassWord){
        return accountRepository.
                resetPassword(accountWithNewPassWord.getUserName(),accountWithNewPassWord.getPassword()) == 1;
    }




}
