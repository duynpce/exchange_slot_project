package Main.Service;


import Main.DTO.ExchangeSlotRequest.ExchangeSlotRequestResponseDTO;
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
    public void add(ExchangeSlotRequest exchangeSlotRequest) {

        exchangeSlotRequestRepository.save(exchangeSlotRequest);
    }

    @CacheEvict(value = "exchangeSlotData", key = "#id")
    public void deleteById(int id) {
        try {
            exchangeSlotRequestRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException("slot request with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Cacheable(value = "listExchangeSlotData", key = "#classCode")
    public List<ExchangeSlotRequestResponseDTO> findByClassCode(String classCode, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeSlotRequest> data =
                exchangeSlotRequestRepository.findByAccount_ClassCode(classCode, pageable);

        if (data.isEmpty()) {
            throw new BaseException("no slot request with class code: " + classCode, HttpStatus.NOT_FOUND);
        }
        return exchangeSlotRequestMapper.toDtoList(data);
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
    public List<ExchangeSlotRequestResponseDTO> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeSlotRequest> data = exchangeSlotRequestRepository.findByCurrentSlot(slot,pageable);
        if (data.isEmpty()) {
            throw new BaseException("no slot request with slot: " + slot, HttpStatus.NOT_FOUND);
        }
        return exchangeSlotRequestMapper.toDtoList(data);
    }

    @CachePut(value = "exchangeSlotData", key = "#studentCode")
    public ExchangeSlotRequestResponseDTO findByStudentCode(String studentCode) {

        ExchangeSlotRequest data = exchangeSlotRequestRepository.findByAccount_StudentCode(studentCode)
                .orElseThrow(() -> new BaseException("no exchange request found",HttpStatus.NOT_FOUND));

        return exchangeSlotRequestMapper.toDto(data);
    }

    @Cacheable(value = "exchangeSlotExists", key = "#studentCode")
    public boolean existsByStudentCode(String studentCode){
        return exchangeSlotRequestRepository.existsByAccount_StudentCode(studentCode);
    }



}
