package main.service;


import main.constant.IntConstant;
import main.exception.BaseException;
import main.entity.ExchangeSlotRequest;
import main.repository.ExchangeSlotRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeSlotRequestService {
    private final int pageSize = IntConstant.DEFAULT_PAGE_SIZE.getValue();
    private final String cacheData = "exchangeSlotData";
    private final String cacheExists = "exchangeSlotExists";
    private final String cacheListData = "listExchangeSlotData";

    private final ExchangeSlotRequestRepository exchangeSlotRequestRepository;

    @Caching(
            put = {
                    @CachePut(value = cacheData, key = "#request.studentCode"),
                    @CachePut(value = cacheData, key = "#request.id"),
            },
            evict = {
                    @CacheEvict(value = cacheExists, key = "#request.studentCode"),
                    @CacheEvict(value = cacheListData, allEntries = true),
            }
    )
    public ExchangeSlotRequest add(ExchangeSlotRequest request) {

        return exchangeSlotRequestRepository.save(request);
    }

    @Caching(evict = {
            @CacheEvict(value = cacheData, key = "#request.studentCode"),
            @CacheEvict(value = cacheData, key = "#request.id"),
            @CacheEvict(value = cacheExists, key = "#request.studentCode"),
            @CacheEvict(value = cacheListData, allEntries = true),
    })
    public void deleteById(ExchangeSlotRequest request) {

        exchangeSlotRequestRepository.deleteById(request.getId());
    }

    @Cacheable(value = cacheListData, key = "{#classCode, #page}")
    public List<ExchangeSlotRequest> findByCurrentClassCode(String classCode, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<ExchangeSlotRequest> data = exchangeSlotRequestRepository.findByCurrentClassCode(classCode, pageable);

        if (data.isEmpty()) {
            throw new BaseException("no slot request with class code: " + classCode, HttpStatus.NOT_FOUND);
        }
        return data;
    }
/// for exchange subject request
//    public List<ExchangeSlotRequest> findBySubjectCode(String subjectCode, int page) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//        List<ExchangeSlotRequest> data = exchangeSlotRequestRepository.findByMajorClass_ClassCode(subjectCode, pageable);
//        if (data.isEmpty()) {
//            throw new BaseException(
//                    "no slot request with subject code: " + subjectCode,
//                    HttpStatus.NOT_FOUND
//            );
//        }
//        return data;
//    }

//    public List<ExchangeSlotRequest> findByClassCodeAndSubjectCode(String classCode, String subjectCode, int page) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//        List<ExchangeSlotRequest> data =
//                exchangeSlotRequestRepository.findByMajorClass_ClassCodeAndSubject_SubjectCode(classCode, subjectCode,pageable);
//        if (data.isEmpty()) {
//            throw new BaseException(
//                    "no slot request with class code: " + classCode + " and subject code: " + subjectCode,
//                    HttpStatus.NOT_FOUND
//            );
//        }
//        return data;
//    }

    @Cacheable(value = cacheListData, key = "{#slot, #page}")
    public List<ExchangeSlotRequest> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<ExchangeSlotRequest> data = exchangeSlotRequestRepository.findByCurrentSlot(slot, pageable);

        if (data.isEmpty()) {
            throw new BaseException("no slot request with slot: " + slot, HttpStatus.NOT_FOUND);
        }
        return data;
    }

    @Cacheable(value = cacheData, key = "#id")
    public ExchangeSlotRequest findById(int id) {
        return exchangeSlotRequestRepository.findById(id)
                .orElseThrow(() -> new BaseException("not found request with id : " + id, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = cacheData, key = "#studentCode")
    public ExchangeSlotRequest findByStudentCode(String studentCode) {
        return exchangeSlotRequestRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new BaseException("no exchange request found", HttpStatus.NOT_FOUND));
    }


//    @Cacheable(value = cacheData,   key = "#accountId + '-accountId'" )
    public ExchangeSlotRequest findByAccountId(int accountId) {
        return exchangeSlotRequestRepository.findByAccountId(accountId).orElse(null);
    }

    @Cacheable(value = cacheExists, key = "#studentCode")
    public boolean existsByStudentCode(String studentCode) {
        return exchangeSlotRequestRepository.existsByStudentCode(studentCode);
    }
}
