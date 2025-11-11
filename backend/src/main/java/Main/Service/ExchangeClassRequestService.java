package Main.Service;

<<<<<<< HEAD
import Main.DTO.ResponseDTO;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Repository.ExchangeClassRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ExchangeClassRequestService {/// temporary
    @Autowired
    ExchangeClassRequestRepository exchangeClassRequestRepository;


    public List<ExchangeClassRequest> findByClassCode (String classCode){
        return exchangeClassRequestRepository.findByClassCode(classCode);
    }


    public boolean add(ExchangeClassRequest exchangeClassRequest){
        boolean alreadyExisted= exchangeClassRequestRepository.existsByStudentCode(exchangeClassRequest.getStudentCode());
        if(alreadyExisted) return false; /// allow only one exchange Class request by student's code

        return exchangeClassRequestRepository.save(exchangeClassRequest).getID() != 0;///save return enity
    }

    public boolean delete(ExchangeClassRequest exchangeClassRequest){
        ResponseDTO ResponseDTO = new ResponseDTO();
        exchangeClassRequestRepository.delete(exchangeClassRequest);

        return true; /// if delete failed --> throw exception --> global handler handle
    }

=======
import Main.DTO.ExchangeClassRequest.ExchangeClassRequestResponseDTO;
import Main.Enum.Constant;
import Main.Exception.BaseException;
import Main.Mapper.ExchangeClassRequestMapper;
import Main.Entity.ExchangeClassRequest;
import Main.Repository.ExchangeClassRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExchangeClassRequestService {
    private final int pageSize = Constant.DefaultPageSize.getPageSize();

    @Autowired
    ExchangeClassRequestRepository exchangeClassRequestRepository;

    @Autowired
    ExchangeClassRequestMapper exchangeClassRequestMapper;

    @Caching(evict = {  /// add later
            @CacheEvict(value = "exchangeClassExists", key = "#exchangeClassRequest.studentCode")
    })

    public ExchangeClassRequest add(ExchangeClassRequest exchangeClassRequest) {
         return exchangeClassRequestRepository.save(exchangeClassRequest); //default id =0 , save return new Entity
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "listExchangeClassData", allEntries = true),
            },
            put ={
                    @CachePut(value = "exchangeClassData", key = "#request.studentCode"),
                    @CachePut(value = "exchangeClassData", key ="#request.id"),
            }
    )
    public ExchangeClassRequest update(ExchangeClassRequest request) {
        return exchangeClassRequestRepository.save(request);
    }

    @Caching(evict =  {
            @CacheEvict(value = "exchangeClassData", key ="#request.id"),
            @CacheEvict(value = "exchangeClassData", key ="#request.studentCode"),
    })
    public void deleteById(ExchangeClassRequest request) {
        exchangeClassRequestRepository.deleteById(request.getId());
    }

    @Cacheable(value = "listExchangeClassData", key = "#classCode")
    public List<ExchangeClassRequest> findByClassCode(String classCode , int page) {

        Pageable pageable = PageRequest.of(page, pageSize); //page is which page, pageSize is number of element in a page
        List<ExchangeClassRequest> data = exchangeClassRequestRepository.
                findByAccount_ClassCode(classCode,pageable);

        if (data.isEmpty()) {
            throw new BaseException("no request with that class code: " + classCode, HttpStatus.NOT_FOUND);
        }

        return data;
    }

    @Cacheable(value = "listExchangeClassData", key = "#slot")
    public List<ExchangeClassRequest> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeClassRequest> data = exchangeClassRequestRepository.findByCurrentSlot(slot,pageable);

        if (data.isEmpty()) {
            throw new BaseException("no slot request with slot: " + slot, HttpStatus.NOT_FOUND);
        }

        return data;
    }

    @Cacheable(value = "exchangeClassData", key = "#studentCode")
    public ExchangeClassRequest findByStudentCode(String studentCode) {

        return exchangeClassRequestRepository.findByAccount_StudentCode(studentCode)
                .orElseThrow(()-> new BaseException("no request with student code "+ studentCode,HttpStatus.NOT_FOUND));

    }


    @Cacheable(value = "exchangeClassData", key ="#id")
    public ExchangeClassRequest findById(int id){
        return exchangeClassRequestRepository.findById(id).
                orElseThrow(() -> new BaseException(" not found request with id : " + id, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = "exchangeClassExists", key ="#studentCode")
    public boolean existsByStudentCode(String studentCode){
        return exchangeClassRequestRepository.existsByAccount_StudentCode(studentCode);
    }






>>>>>>> develop
}
