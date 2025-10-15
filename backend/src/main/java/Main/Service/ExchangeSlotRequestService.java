package Main.Service;


import Main.DTO.ExchangeSlotRequestResponseDTO;
import Main.Exception.ExchangeSlotRequestException;
import Main.Mapper.ExchangeSlotRequestMapper;
import Main.Model.Enity.ExchangeSlotRequest;
import Main.Repository.ExchangeSlotRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeSlotRequestService {

    private final int pageSize = 20;

    @Autowired
    ExchangeSlotRequestRepository exchangeSlotRequestRepository;

    @Autowired
    ExchangeSlotRequestMapper exchangeSlotRequestMapper;

    public boolean add(ExchangeSlotRequest exchangeSlotRequest) {

        ExchangeSlotRequest saved = exchangeSlotRequestRepository.save(exchangeSlotRequest);
        if (saved.getId() == 0) {
            throw new ExchangeSlotRequestException(
                    "failed to add slot request due to internal error",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return true;
    }

    public void deleteById(int id) {
        try {
            exchangeSlotRequestRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ExchangeSlotRequestException(
                    "slot request with id " + id + " not found",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public List<ExchangeSlotRequestResponseDTO> findByClassCode(String classCode, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeSlotRequest> data = 
                exchangeSlotRequestRepository.findByAccount_ClassCode(classCode, pageable);

        if (data.isEmpty()) {
            throw new ExchangeSlotRequestException(
                    "no slot request with class code: " + classCode,
                    HttpStatus.NOT_FOUND
            );
        }
        return exchangeSlotRequestMapper.toDtoList(data);
    }
/// for exchange subject request
//    public List<ExchangeSlotRequest> findBySubjectCode(String subjectCode, int page) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//        List<ExchangeSlotRequest> data = exchangeSlotRequestRepository.findByMajorClass_ClassCode(subjectCode, pageable);
//        if (data.isEmpty()) {
//            throw new ExchangeSlotRequestException(
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
//            throw new ExchangeSlotRequestException(
//                    "no slot request with class code: " + classCode + " and subject code: " + subjectCode,
//                    HttpStatus.NOT_FOUND
//            );
//        }
//        return data;
//    }

    public List<ExchangeSlotRequestResponseDTO> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeSlotRequest> data = exchangeSlotRequestRepository.findByCurrentSlot(slot,pageable);
        if (data.isEmpty()) {
            throw new ExchangeSlotRequestException(
                    "no slot request with slot: " + slot,
                    HttpStatus.NOT_FOUND
            );
        }
        return exchangeSlotRequestMapper.toDtoList(data);
    }

    public boolean existsByStudentCode(String studentCode){
        return exchangeSlotRequestRepository.existsByAccount_StudentCode(studentCode);
    }



}
