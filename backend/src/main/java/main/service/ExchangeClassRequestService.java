package main.service;


import main.constant.IntConstant;
import main.exception.BaseException;

import main.entity.ExchangeClassRequest;
import main.repository.ExchangeClassRequestRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeClassRequestService {
    private static final int PAGE_SIZE = IntConstant.DEFAULT_PAGE_SIZE.getValue();
    private static final String CACHE_DATA = "exchangeClassData";
    private static final String CACHE_EXISTS = "exchangeClassExists";
    private static final String CACHE_LIST_DATA = "listExchangeClassData";

    private final ExchangeClassRequestRepository exchangeClassRequestRepository;

    @Caching(
        cacheable = {
            @Cacheable(value = CACHE_DATA, key = "#request.studentCode"),
            @Cacheable(value = CACHE_DATA, key ="#request.id"),
        },
        evict = {
            @CacheEvict(value = CACHE_EXISTS, key = "#request.studentCode"),
            @CacheEvict(value = CACHE_LIST_DATA, allEntries = true),
        }
    )
    public ExchangeClassRequest add(ExchangeClassRequest request) {
        return exchangeClassRequestRepository.save(request);
    }

    @Caching(
            put ={
                    @CachePut(value = CACHE_DATA, key = "#request.studentCode"),
                    @CachePut(value = CACHE_DATA, key ="#request.id"),
            },
            evict = {
                    @CacheEvict(value = CACHE_EXISTS, key = "#request.studentCode"),
                    @CacheEvict(value = CACHE_LIST_DATA, allEntries = true),
            }
    )
    public ExchangeClassRequest update(ExchangeClassRequest request) {
        return exchangeClassRequestRepository.save(request);
    }

    @Caching(evict =  {
            @CacheEvict(value = CACHE_DATA, key = "#request.studentCode"),
            @CacheEvict(value = CACHE_DATA, key = "#request.id"),
            @CacheEvict(value = CACHE_EXISTS, key = "#request.studentCode"),
            @CacheEvict(value = CACHE_LIST_DATA, allEntries = true),
    })
    public void deleteById(ExchangeClassRequest request) {
        exchangeClassRequestRepository.deleteById(request.getId());
    }

    @Cacheable(value = CACHE_LIST_DATA, key = "{#classCode, #page}")
    public List<ExchangeClassRequest> findByClassCode(String classCode , int page) {

        Pageable pageable = PageRequest.of(page, PAGE_SIZE); //page is which page, PAGE_SIZE is number of element in a page
        List<ExchangeClassRequest> data = exchangeClassRequestRepository.
                findByCurrentClassCode(classCode,pageable);

        if (data.isEmpty()) {
            throw new BaseException("no request with that class code: " + classCode, HttpStatus.NOT_FOUND);
        }

        return data;
    }

    @Cacheable(value = CACHE_LIST_DATA, key = "{#slot, #page}")
    public List<ExchangeClassRequest> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        List<ExchangeClassRequest> data = exchangeClassRequestRepository.findByCurrentSlot(slot,pageable);

        if (data.isEmpty()) {
            throw new BaseException("no slot request with slot: " + slot, HttpStatus.NOT_FOUND);
        }

        return data;
    }

    @Cacheable(value = CACHE_DATA, key = "#studentCode")
    public ExchangeClassRequest findByStudentCode(String studentCode) {

        return exchangeClassRequestRepository.findByStudentCode(studentCode)
                .orElseThrow(()-> new BaseException("no request with student code "+ studentCode,HttpStatus.NOT_FOUND));

    }


    @Cacheable(value = CACHE_DATA, key ="#id")
    public ExchangeClassRequest findById(int id){
        return exchangeClassRequestRepository.findById(id).
                orElseThrow(() -> new BaseException(" not found request with id : " + id, HttpStatus.NOT_FOUND));
    }

//    @Cacheable(value = CACHE_DATA, key = "#accountId + '-accountId'" )
    public ExchangeClassRequest findByAccountId(int accountId){
        return exchangeClassRequestRepository.findByAccountId(accountId).orElse(null);
    }

    @Cacheable(value = CACHE_EXISTS, key ="#studentCode")
    public boolean existsByStudentCode(String studentCode){
        return exchangeClassRequestRepository.existsByStudentCode(studentCode);
    }






}
