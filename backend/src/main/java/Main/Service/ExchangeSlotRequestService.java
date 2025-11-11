package Main.Service;


<<<<<<< HEAD
import Main.DTO.ExchangeClassRequestDTO;
import Main.DTO.ResponseDTO;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Model.Enity.ExchangeSlotRequest;
import Main.Repository.ExchangeSlotRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
=======
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
>>>>>>> develop

import java.util.List;

@Service
<<<<<<< HEAD
public class ExchangeSlotRequestService {
    @Autowired
    ExchangeSlotRequestRepository exchangeSlotRequestRepository;

    public List<ExchangeSlotRequest> findByClassCode (String classCode){
        return exchangeSlotRequestRepository.findByClassCode(classCode);
    }

    public List<ExchangeSlotRequest> findBySubjectCode(String subjectCode){
        return exchangeSlotRequestRepository.findBySubjectCode(subjectCode);
    }

    public List<ExchangeSlotRequest> findByClassCodeAndSubjectCode(String classCode,String subjectCode){
        return exchangeSlotRequestRepository.findByClassCodeAndSubjectCode(classCode,subjectCode);
    }

    public boolean add(ExchangeSlotRequest exchangeSlotRequest){
        boolean alreadyExisted=
                exchangeSlotRequestRepository.
                        existsByStudentCodeAndSubjectCode
                                (exchangeSlotRequest.getStudentCode(), exchangeSlotRequest.getSubjectCode());
        if(alreadyExisted) return false;

        exchangeSlotRequestRepository.save(exchangeSlotRequest);
        return true;

    }

    public boolean delete(ExchangeSlotRequest exchangeSlotRequest){
        exchangeSlotRequestRepository.delete(exchangeSlotRequest);

        return true;
    }
=======
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

    @Caching(evict = {
            @CacheEvict(value = "exchangeSlotData", key = "#request.id"),
            @CacheEvict(value = "exchangeSlotData", key = "#request.studentCode")
    })
    public void deleteById(ExchangeSlotRequest request) {
        try {
            exchangeSlotRequestRepository.deleteById(request.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException("slot request with id " + request.getId() + " not found", HttpStatus.NOT_FOUND);
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

    @Cacheable(value = "exchangeSlotData", key ="#id")
    public ExchangeSlotRequest findById(int id){
        return exchangeSlotRequestRepository.findById(id).
                orElseThrow(() -> new BaseException(" not found request with id : " + id, HttpStatus.NOT_FOUND));
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



>>>>>>> develop
}
