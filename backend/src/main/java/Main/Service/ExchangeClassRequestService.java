package Main.Service;

import Main.Exception.ExchangeClassRequestException;
import Main.Exception.ExchangeSlotRequestException;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Model.Enity.ExchangeSlotRequest;
import Main.Repository.ExchangeClassRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class ExchangeClassRequestService {/// temporary
    private final int pageSize = 20;

    @Autowired
    ExchangeClassRequestRepository exchangeClassRequestRepository;



    public boolean add(ExchangeClassRequest exchangeClassRequest) {
        boolean alreadyExisted = exchangeClassRequestRepository.
                existsByAccount_StudentCode(exchangeClassRequest.getAccount().getStudentCode());
        if (alreadyExisted) {
            throw new ExchangeClassRequestException("already existed request for this student code", HttpStatus.CONFLICT);
        }

        return exchangeClassRequestRepository.save(exchangeClassRequest).getId() != 0; //default id =0 , save return new Enity
    }

    public void deleteById(int id) {

        try{
            exchangeClassRequestRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ExchangeClassRequestException("no request with id :" + id,HttpStatus.NOT_FOUND);//throw custom exception if delete failed
        }

    }


    public List<ExchangeClassRequest> findByClassCode(String classCode , int page) {

        Pageable pageable = PageRequest.of(page, pageSize); //page is which page, pageSize is number of element which page
        List<ExchangeClassRequest> requestFound = exchangeClassRequestRepository.
                findByMajorClass_ClassCode(classCode,pageable);

        if (requestFound.isEmpty()) {
            throw new ExchangeClassRequestException("no request with that class code: " + classCode, HttpStatus.NOT_FOUND);
        }

        return requestFound;
    }

    public List<ExchangeClassRequest> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        List<ExchangeClassRequest> requestFound = exchangeClassRequestRepository.findByMajorClass_Slot(slot,pageable);
        if (requestFound.isEmpty()) {
            throw new ExchangeSlotRequestException(
                    "no slot request with slot: " + slot,
                    HttpStatus.NOT_FOUND
            );
        }
        return requestFound;
    }



}
