package Main.Service;


import Main.Enum.IntConstant;
import Main.Exception.BaseException;

import Main.Entity.ExchangeClassRequest;
import Main.Repository.ExchangeClassRequestRepository;

import Main.Utility.CacheUtil;
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
    private final int pageSize = IntConstant.DEFAULT_PAGE_SIZE.getValue();
    private final String cacheData = "exchangeClassData";
    private final String cacheExists = "exchangeClassExists";
    private final String cacheListData = "listExchangeClassData";

    private final ExchangeClassRequestRepository exchangeClassRequestRepository;
    private final CacheUtil<ExchangeClassRequest> cacheUtil;
    @Caching(
        cacheable = {
            @Cacheable(value = cacheData, key = "#request.studentCode"),
            @Cacheable(value = cacheData, key ="#request.id"),
        },
        evict = {
            @CacheEvict(value = cacheExists, key = "#request.studentCode"),
            @CacheEvict(value = cacheListData, allEntries = true),
        }
    )
    public ExchangeClassRequest add(ExchangeClassRequest request) {
        return exchangeClassRequestRepository.save(request);
    }

    @Caching(
            put ={
                    @CachePut(value = cacheData, key = "#request.studentCode"),
                    @CachePut(value = cacheData, key ="#request.id"),
            },
            evict = {
                    @CacheEvict(value = cacheExists, key = "#request.studentCode"),
                    @CacheEvict(value = cacheListData, allEntries = true),
            }
    )
    public ExchangeClassRequest update(ExchangeClassRequest request) {
        return exchangeClassRequestRepository.save(request);
    }

    @Caching(evict =  {
            @CacheEvict(value = cacheData, key = "#request.studentCode"),
            @CacheEvict(value = cacheData, key = "#request.id"),
            @CacheEvict(value = cacheExists, key = "#request.studentCode"),
            @CacheEvict(value = cacheListData, allEntries = true),
    })
    public void deleteById(ExchangeClassRequest request) {
        exchangeClassRequestRepository.deleteById(request.getId());
    }

    @Cacheable(value = cacheListData, key = "{#classCode, #page}")
    public List<ExchangeClassRequest> findByClassCode(String classCode , int page) {

        Pageable pageable = PageRequest.of(page, pageSize); //page is which page, pageSize is number of element in a page
        List<ExchangeClassRequest> data = exchangeClassRequestRepository.
                findByCurrentClassCode(classCode,pageable);

        if (data.isEmpty()) {
            throw new BaseException("no request with that class code: " + classCode, HttpStatus.NOT_FOUND);
        }

        return data;
    }

    @Cacheable(value = cacheListData, key = "{#slot, #page}")
    public List<ExchangeClassRequest> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeClassRequest> data = exchangeClassRequestRepository.findByCurrentSlot(slot,pageable);

        if (data.isEmpty()) {
            throw new BaseException("no slot request with slot: " + slot, HttpStatus.NOT_FOUND);
        }

        return data;
    }

    @Cacheable(value = cacheData, key = "#studentCode")
    public ExchangeClassRequest findByStudentCode(String studentCode) {

        return exchangeClassRequestRepository.findByStudentCode(studentCode)
                .orElseThrow(()-> new BaseException("no request with student code "+ studentCode,HttpStatus.NOT_FOUND));

    }


    @Cacheable(value = cacheData, key ="#id")
    public ExchangeClassRequest findById(int id){
        return exchangeClassRequestRepository.findById(id).
                orElseThrow(() -> new BaseException(" not found request with id : " + id, HttpStatus.NOT_FOUND));
    }

//    @Cacheable(value = cacheData, key = "#accountId + '-accountId'" )
    public ExchangeClassRequest findByAccountId(int accountId){
        return exchangeClassRequestRepository.findByAccountId(accountId).orElse(null);
    }

    @Cacheable(value = cacheExists, key ="#studentCode")
    public boolean existsByStudentCode(String studentCode){
        return exchangeClassRequestRepository.existsByStudentCode(studentCode);
    }






}
