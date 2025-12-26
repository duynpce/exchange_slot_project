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
    private final int PAGE_SIZE = IntConstant.DEFAULT_PAGE_SIZE.getValue();
    private final String CACHE_DATA = "exchangeSlotData";
    private final String CACHE_EXISTS = "exchangeSlotExists";
    private final String CACHE_LIST_DATA = "listExchangeSlotData";

    private final ExchangeSlotRequestRepository exchangeSlotRequestRepository;

    @Caching(
            put = {
                    @CachePut(value = CACHE_DATA, key = "#request.studentCode"),
                    @CachePut(value = CACHE_DATA, key = "#request.id"),
            },
            evict = {
                    @CacheEvict(value = CACHE_EXISTS, key = "#request.studentCode"),
                    @CacheEvict(value = CACHE_LIST_DATA, allEntries = true),
            }
    )
    public ExchangeSlotRequest add(ExchangeSlotRequest request) {

        return exchangeSlotRequestRepository.save(request);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_DATA, key = "#request.studentCode"),
            @CacheEvict(value = CACHE_DATA, key = "#request.id"),
            @CacheEvict(value = CACHE_EXISTS, key = "#request.studentCode"),
            @CacheEvict(value = CACHE_LIST_DATA, allEntries = true),
    })
    public void deleteById(ExchangeSlotRequest request) {

        exchangeSlotRequestRepository.deleteById(request.getId());
    }

    @Cacheable(value = CACHE_LIST_DATA, key = "{#classCode, #page}")
    public List<ExchangeSlotRequest> findByCurrentClassCode(String classCode, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<ExchangeSlotRequest> data = exchangeSlotRequestRepository.findByCurrentClassCode(classCode, pageable);

        if (data.isEmpty()) {
            throw new BaseException("no slot request with class code: " + classCode, HttpStatus.NOT_FOUND);
        }
        return data;
    }
/// for exchange subject request
//    public List<ExchangeSlotRequest> findBySubjectCode(String subjectCode, int page) {
//        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
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
//        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
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

    @Cacheable(value = CACHE_LIST_DATA, key = "{#slot, #page}")
    public List<ExchangeSlotRequest> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<ExchangeSlotRequest> data = exchangeSlotRequestRepository.findByCurrentSlot(slot, pageable);

        if (data.isEmpty()) {
            throw new BaseException("no slot request with slot: " + slot, HttpStatus.NOT_FOUND);
        }
        return data;
    }

    @Cacheable(value = CACHE_DATA, key = "#id")
    public ExchangeSlotRequest findById(int id) {
        return exchangeSlotRequestRepository.findById(id)
                .orElseThrow(() -> new BaseException("not found request with id : " + id, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = CACHE_DATA, key = "#studentCode")
    public ExchangeSlotRequest findByStudentCode(String studentCode) {
        return exchangeSlotRequestRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new BaseException("no exchange request found", HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = CACHE_EXISTS, key = "#studentCode")
    public boolean existsByStudentCode(String studentCode) {
        return exchangeSlotRequestRepository.existsByStudentCode(studentCode);
    }
}
