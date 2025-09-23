package Main.Service;

import Main.DTO.ResponseDTO;
import Main.Exception.ExchangeClassRequestException;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Repository.ExchangeClassRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ExchangeClassRequestService {/// temporary
    @Autowired
    ExchangeClassRequestRepository exchangeClassRequestRepository;



    public boolean add(ExchangeClassRequest exchangeClassRequest) {
        boolean alreadyExisted = exchangeClassRequestRepository.existsByStudentCode(exchangeClassRequest.getStudentCode());
        if (alreadyExisted) {
            throw new ExchangeClassRequestException("already existed request for this student code", HttpStatus.CONFLICT);
        }

        return exchangeClassRequestRepository.save(exchangeClassRequest).getID() != 0; //default id =0 , save return new Enity
    }

    public void deleteById(int id) {

        try{
            exchangeClassRequestRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ExchangeClassRequestException("no request with id :" + id,HttpStatus.NOT_FOUND);//throw custom exception if delete failed
        }

    }


    public List<ExchangeClassRequest> findByClassCode(String classCode) {
        List<ExchangeClassRequest> result = exchangeClassRequestRepository.findByClassCode(classCode);

        if (result.isEmpty()) {
            throw new ExchangeClassRequestException("no request with that class code: " + classCode, HttpStatus.NOT_FOUND);
        }

        return result;
    }

}
