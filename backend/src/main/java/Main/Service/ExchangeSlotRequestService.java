package Main.Service;


import Main.DTO.ExchangeSlotRequest.ExchangeSlotRequestResponseDTO;
import Main.Entity.ExchangeClassRequest;
import Main.Enum.Constant;
import Main.Exception.BaseException;
import Main.Mapper.ExchangeSlotRequestMapper;
import Main.Entity.ExchangeSlotRequest;
import Main.Repository.ExchangeSlotRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExchangeSlotRequestService {

    private final int pageSize = Constant.DefaultPageSize.getPageSize();

    @Autowired
    ExchangeSlotRequestRepository exchangeSlotRequestRepository;

    @Autowired
    ExchangeSlotRequestMapper exchangeSlotRequestMapper;

    @Caching(evict = {  ///  add later
            @CacheEvict(value = "exchangeSlotExists", key = "#exchangeSlotRequest.studentCode")
    })
    public ExchangeSlotRequest add(ExchangeSlotRequest exchangeSlotRequest) {
        return exchangeSlotRequestRepository.save(exchangeSlotRequest);
    }

    @Caching(evict = {
            @CacheEvict(value = "exchangeSlotData", key = "#request.id"),
            @CacheEvict(value = "exchangeSlotData", key = "#request.studentCode")
    })
    public void deleteById(ExchangeSlotRequest request) {
            exchangeSlotRequestRepository.deleteById(request.getId());
    }


    @Cacheable(value = "listExchangeSlotData", key = "#classCode")
    public List<ExchangeSlotRequest> findByClassCode(String classCode, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeSlotRequest> data =
                exchangeSlotRequestRepository.findByAccount_ClassCode(classCode, pageable);

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

    @Cacheable(value = "listExchangeSlotData", key = "#slot")
    public List<ExchangeSlotRequest> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeSlotRequest> data = exchangeSlotRequestRepository.findByCurrentSlot(slot,pageable);
        if (data.isEmpty()) {
            throw new BaseException("no slot request with slot: " + slot, HttpStatus.NOT_FOUND);
        }
        return data;
    }

    @Cacheable(value = "exchangeSlotData", key ="#id")
    public ExchangeSlotRequest findById(int id){
        return exchangeSlotRequestRepository.findById(id).
                orElseThrow(() -> new BaseException(" not found request with id : " + id, HttpStatus.NOT_FOUND));
    }

    @CachePut(value = "exchangeSlotData", key = "#studentCode")
    public ExchangeSlotRequest findByStudentCode(String studentCode) {

        ExchangeSlotRequest data = exchangeSlotRequestRepository.findByAccount_StudentCode(studentCode)
                .orElseThrow(() -> new BaseException("no exchange request found",HttpStatus.NOT_FOUND));

        return data;
    }

    @Cacheable(value = "exchangeSlotExists", key = "#studentCode")
    public boolean existsByStudentCode(String studentCode){
        return exchangeSlotRequestRepository.existsByAccount_StudentCode(studentCode);
    }



}
