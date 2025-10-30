package Main.Service;

import Main.DTO.ExchangeClassRequest.ExchangeClassRequestResponseDTO;
import Main.Enum.Constant;
import Main.Exception.BaseException;
import Main.Mapper.ExchangeClassRequestMapper;
import Main.Entity.ExchangeClassRequest;
import Main.Repository.ExchangeClassRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
         return exchangeClassRequestRepository.save(exchangeClassRequest); //default id =0 , save return new Enity
    }

    @Caching(evict = {
            @CacheEvict(value = "exchangeClassData", key = "#updatedRequest.id"),
            @CacheEvict(value = "listExchangeClassData", allEntries = true),
            @CacheEvict(value = "exchangeClassExists", key = "#updatedRequest``.studentCode")
    })
    public ExchangeClassRequestResponseDTO update(ExchangeClassRequest updatedRequest) {

        ExchangeClassRequest existing = exchangeClassRequestRepository.findById(updatedRequest.getId())
                .orElseThrow(() -> new BaseException(
                        "No exchange request found with id: " + updatedRequest.getId(), HttpStatus.NOT_FOUND
                ));

        if (!existing.getStudentCode().equals(updatedRequest.getStudentCode())) {
            throw new BaseException("Cannot change studentCode, can only change in account", HttpStatus.BAD_REQUEST);
        }

        existing.setDesiredClassCode(updatedRequest.getDesiredClassCode());

        ExchangeClassRequest saved = exchangeClassRequestRepository.save(existing);
        return exchangeClassRequestMapper.toDto(saved);
    }


    @CacheEvict(value = "exchangeClassData", key ="#id")
    public void deleteById(int id) {

        try{
            exchangeClassRequestRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new BaseException("no request with id :" + id,HttpStatus.NOT_FOUND);//throw custom exception if delete failed
        }

    }

    @Cacheable(value = "listExchangeClassData", key = "#classCode")
    public List<ExchangeClassRequestResponseDTO> findByClassCode(String classCode , int page) {

        Pageable pageable = PageRequest.of(page, pageSize); //page is which page, pageSize is number of element in a page
        List<ExchangeClassRequest> data = exchangeClassRequestRepository.
                findByAccount_ClassCode(classCode,pageable);

        if (data.isEmpty()) {
            throw new BaseException("no request with that class code: " + classCode, HttpStatus.NOT_FOUND);
        }

        return exchangeClassRequestMapper.toDtoList(data);
    }

    @Cacheable(value = "listExchangeClassData", key = "#slot")
    public List<ExchangeClassRequestResponseDTO> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeClassRequest> data = exchangeClassRequestRepository.findByCurrentSlot(slot,pageable);

        if (data.isEmpty()) {
            throw new BaseException("no slot request with slot: " + slot, HttpStatus.NOT_FOUND);
        }

        return exchangeClassRequestMapper.toDtoList(data);
    }

    @Cacheable(value = "exchangeStudentData", key = "#studentCode")
    public ExchangeClassRequestResponseDTO findByStudentCode(String studentCode) {

        ExchangeClassRequest data = exchangeClassRequestRepository.findByAccount_StudentCode(studentCode)
                .orElseThrow(()-> new BaseException("no request with student code "+ studentCode,HttpStatus.NOT_FOUND));

        return exchangeClassRequestMapper.toDto(data);
    }


    @Cacheable(value = "exchangeClassData", key ="#id")
    public ExchangeClassRequestResponseDTO findById(int id){
        ExchangeClassRequest request = exchangeClassRequestRepository.findById(id).
                orElseThrow(() -> new BaseException(" not found request with id : " + id, HttpStatus.NOT_FOUND));
        return exchangeClassRequestMapper.toDto(request);
    }

    @Cacheable(value = "exchangeClassExists", key ="#studentCode")
    public boolean existsByStudentCode(String studentCode){
        return exchangeClassRequestRepository.existsByAccount_StudentCode(studentCode);
    }






}
