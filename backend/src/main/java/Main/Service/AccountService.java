package Main.Service;


import Main.DTO.LoginRequestDTO;
import Main.DTO.LoginResponseDTO;
import Main.DTO.ResetPasswordDTO;
import Main.Exception.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import Main.Model.Enity.Account;
import Main.Repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public boolean register(Account account){
        boolean alreadyExistedUserName = accountRepository.existsByUsername(account.getUsername());
        boolean alreadyExistedPhoneNumber = accountRepository.existsByPhoneNumber(account.getPhoneNumber());
        boolean alreadyExistedStudentCode = accountRepository.existsByStudentCode(account.getStudentCode());
        boolean alreadyExistedAccountName = accountRepository.existsByAccountName(account.getAccountName());

        if(alreadyExistedUserName){
            throw new AccountException("existed user name", HttpStatus.CONFLICT);
        }
        else if(alreadyExistedPhoneNumber){
            throw new AccountException("existed phone number", HttpStatus.CONFLICT);
        }
        else if(alreadyExistedStudentCode){
            throw new AccountException("existed student code", HttpStatus.CONFLICT);
        }
        else if(alreadyExistedAccountName){
            throw new AccountException("existed account name", HttpStatus.CONFLICT);
        }

        String encryptedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encryptedPassword);
        accountRepository.save(account);

        return true;
    }

    public  Account login(LoginRequestDTO loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);

        boolean hasFoundAccount = optionalAccount.isPresent();
        if(hasFoundAccount) {
            String encryptedPassword = optionalAccount.get().getPassword();
            boolean correctPassword = passwordEncoder.matches(password,encryptedPassword );

            if(correctPassword) {return optionalAccount.get();}

           throw new AccountException("incorrect password" , HttpStatus.UNAUTHORIZED);
        }

        throw new AccountException("no account found with username " + username, HttpStatus.NOT_FOUND);

    }

    public boolean resetPassword(ResetPasswordDTO resetPasswordDTO){

        String username = resetPasswordDTO.getUsername();
        String encryptedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());

        return accountRepository.resetPassword(username,encryptedPassword) == 1;
    }

    public Account findByUserName(String userName){
        return accountRepository.findByUsername(userName).orElse(null);
    }

    public boolean existsByStudentCode(String studentCode){
        return accountRepository.existsByStudentCode(studentCode);
    }




}
