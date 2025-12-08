package Main.Service;


import Main.Config.Security.UserDetailConfig;
import Main.Config.Security.UserDetailServiceConfig;
import Main.DTO.Auth.*;
import Main.DTO.Common.ResponseDTO;
import Main.Entity.ExchangeClassRequest;
import Main.Entity.ExchangeSlotRequest;
import Main.Exception.BaseException;
import Main.Utility.CacheUtil;
import Main.Utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Main.Entity.Account;
import Main.Repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.logging.Logger;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final String cacheData = "accountData";
    private final String cacheExists = "accountExists";

    private final AccountRepository accountRepository;

    @Caching(
            put = {
                    @CachePut(value = cacheData, key = "#account.username"),
                    @CachePut(value = cacheData, key = "#account.studentCode"),
                    @CachePut(value = cacheData, key = "#account.phoneNumber"),
            },
            evict = {
                    @CacheEvict(value = cacheExists, key = "#account.studentCode"),

            }

    )
    public Account update(Account account) {

        return accountRepository.save(account);
    }

    @Cacheable(value = cacheData, key = "#username")
    public Account findByUserName(String username) {
        return accountRepository.findByUsername(username).orElseThrow
                (() -> new BaseException("not found account with username : " + username, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = cacheData , key = "#studentCode")
    public Account findByStudentCode(String studentCode){
        return  accountRepository.findByStudentCode(studentCode).orElseThrow
                (() -> new BaseException("not found account with studentCode : " + studentCode, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = cacheExists, key = "#studentCode")
    public boolean existsByStudentCode(String studentCode){
        return accountRepository.existsByStudentCode(studentCode);
    }

    @Cacheable(value = cacheExists, key = "#username")
    public boolean existsByUsername(String username){
        return accountRepository.existsByUsername(username);
    }

    @Cacheable(value = cacheExists, key = "#accountName")
    public boolean existsByAccountName(String accountName){
        return accountRepository.existsByAccountName(accountName);
    }

    @Cacheable(value = cacheExists, key = "#phoneNumber")
    public boolean existsByPhoneNumber(String phoneNumber){
        return accountRepository.existsByPhoneNumber(phoneNumber);
    }

    @Cacheable(value = cacheExists, key = "#id")
    public boolean existsById(int id){
        return accountRepository.existsById(id);
    }


}
