package Main.Service;


import Main.Entity.ExchangeClassRequest;
import Main.Enum.Constant;
import Main.Exception.BaseException;
import Main.Entity.ExchangeSlotRequest;
import Main.Repository.ExchangeSlotRequestRepository;
import Main.Utility.CacheUtil;
import Main.Utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.Jar;
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
    private final int pageSize = Constant.DefaultPageSize.getPageSize();
    private final String cacheData = "exchangeSlotData";
    private final String cacheExists = "exchangeSlotExists";
    private final String cacheListData = "listExchangeSlotData";

    private final ExchangeSlotRequestRepository exchangeSlotRequestRepository;
    private final CacheUtil<ExchangeSlotRequest> cacheUtil;
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    @Caching(
            cacheable = {
                    @Cacheable(value = cacheData, key = "#exchangeSlotRequest.studentCode"),
                    @Cacheable(value = cacheData, key = "#exchangeSlotRequest.id"),
            },
            evict = {
                    @CacheEvict(value = cacheExists, key = "#exchangeSlotRequest.studentCode"),
            }
    )
    public ExchangeSlotRequest add(ExchangeSlotRequest request) {

        ExchangeSlotRequest savedRequest = exchangeSlotRequestRepository.save(request);
        cacheUtil.addOneItemToList(cacheListData, savedRequest.getCurrentSlot(), savedRequest);

        return savedRequest;
    }

    @Caching(
            put = {
                    @CachePut(value = cacheData, key = "#exchangeSlotRequest.studentCode"),
                    @CachePut(value = cacheData, key = "#exchangeSlotRequest.id"),
            },
            evict = {
                    @CacheEvict(value = cacheExists, key = "#exchangeSlotRequest.studentCode"),
            }
    )
    public ExchangeSlotRequest update(ExchangeSlotRequest request) {
        ExchangeSlotRequest updatedRequest = exchangeSlotRequestRepository.save(request);

        cacheUtil.updateOneItemToList(cacheListData, updatedRequest.getCurrentSlot(), updatedRequest);

        return updatedRequest;
    }

    @Caching(evict = {
            @CacheEvict(value = cacheData, key = "#request.studentCode"),
            @CacheEvict(value = cacheData, key = "#request.id"),
            @CacheEvict(value = cacheExists, key = "#request.studentCode"),
    })
    public void deleteById(ExchangeSlotRequest request) {
        cacheUtil.deleteOneItemFromList(cacheListData, request.getCurrentSlot(), request);

        exchangeSlotRequestRepository.deleteById(request.getId());
    }

    @Cacheable(value = cacheListData, key = "#classCode")
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

    @Cacheable(value = cacheListData, key = "#slot")
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

    public ExchangeSlotRequest findByAccountId(int accountId) {
        return exchangeSlotRequestRepository.findByAccountId(accountId).
                orElseThrow(() -> new BaseException(" not found request with account id : " + accountId, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = cacheExists, key = "#studentCode")
    public boolean existsByStudentCode(String studentCode) {
        return exchangeSlotRequestRepository.existsByStudentCode(studentCode);
    }
}
