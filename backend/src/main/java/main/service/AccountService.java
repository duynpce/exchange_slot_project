package main.service;


import main.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import main.entity.Account;
import main.repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final String CACHE_DATA = "accountData";
    private final String CACHE_EXISTS = "accountExists";

    private final AccountRepository accountRepository;

    // cache put to update data in cache and because cacheable will not be called if data is already in cache
    @Caching(
            put = {
                    @CachePut(value = CACHE_DATA, key = "#account.username"),
                    @CachePut(value = CACHE_DATA, key = "#account.studentCode"),
                    @CachePut(value = CACHE_DATA, key = "#account.phoneNumber"),
            },
            evict = {
                    @CacheEvict(value = CACHE_EXISTS, key = "#account.studentCode"),
                    @CacheEvict(value = CACHE_EXISTS, key = "#account.username"),
                    @CacheEvict(value = CACHE_EXISTS, key = "#account.accountName"),
                    @CacheEvict(value = CACHE_EXISTS, key = "#account.phoneNumber"),
                    @CacheEvict(value = CACHE_EXISTS, key = "#account.email"),
                    @CacheEvict(value = CACHE_EXISTS, key = "#account.id")
            }

    )
    public Account save(Account account) {

        return accountRepository.save(account);
    }



    @Cacheable(value = CACHE_DATA, key = "#username")
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow
                (() -> new BaseException("not found account with username : " + username, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = CACHE_DATA , key = "#studentCode")
    public Account findByStudentCode(String studentCode){
        return  accountRepository.findByStudentCode(studentCode).orElseThrow
                (() -> new BaseException("not found account with studentCode : " + studentCode, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = CACHE_DATA , key = "#email")
    public Account findByEmail(String email){
        return  accountRepository.findByEmail(email).orElseThrow
                (() -> new BaseException("not found account with email : " + email, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = CACHE_EXISTS, key = "#studentCode")
    public boolean existsByStudentCode(String studentCode){
        return accountRepository.existsByStudentCode(studentCode);
    }

    @Cacheable(value = CACHE_EXISTS, key = "#username")
    public boolean existsByUsername(String username){
        return accountRepository.existsByUsername(username);
    }

    @Cacheable(value = CACHE_EXISTS, key = "#accountName")
    public boolean existsByAccountName(String accountName){
        return accountRepository.existsByAccountName(accountName);
    }

    @Cacheable(value = CACHE_EXISTS, key = "#phoneNumber")
    public boolean existsByPhoneNumber(String phoneNumber){
        return accountRepository.existsByPhoneNumber(phoneNumber);
    }

    @Cacheable(value = CACHE_EXISTS, key = "#id")
    public boolean existsById(int id){
        return accountRepository.existsById(id);
    }

    @Cacheable(value = CACHE_EXISTS, key = "#email")
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }


}
