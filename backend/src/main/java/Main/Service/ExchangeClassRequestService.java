package Main.Service;

import Main.DTO.ExchangeClassRequestDTO;
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


    public ExchangeClassRequestDTO Add(ExchangeClassRequest exchangeClassRequest){
        ExchangeClassRequestDTO exchangeClassRequestDTO = new ExchangeClassRequestDTO();
        exchangeClassRequestDTO.setExchangeSuccessfully(exchangeClassRequestRepository.existsByStudentCode(exchangeClassRequest.getStudentCode()));

        if(exchangeClassRequestDTO.getExchangeSuccessfully()){

            exchangeClassRequestDTO.setError("existed request");
            exchangeClassRequestDTO.setMessage("if you want to create new request, please delete the old one");
        }
        else {
            exchangeClassRequestDTO.setError("no error");
            exchangeClassRequestDTO.setMessage("request added successfully");
        }

        return exchangeClassRequestDTO;
    }

}
