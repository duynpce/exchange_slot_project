package Main.Service;

import Main.DTO.ResponseDTO;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Repository.ExchangeClassRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public boolean add(ExchangeClassRequest exchangeClassRequest) {

       try{
           exchangeClassRequestRepository.save(exchangeClassRequest);/// save return entity when success, throw exception if failed
       } catch (DataIntegrityViolationException e) {
           return  false;
       }

        return  true;
    }

    public boolean delete (ExchangeClassRequest exchangeClassRequest){
        return exchangeClassRequestRepository.deleteByStudentCode(exchangeClassRequest.getStudentCode()) == 1;
    }


}
