package Main.Service;

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

    public boolean deleteById(int id){
        exchangeClassRequestRepository.deleteById(id);

        return true; /// if delete failed --> throw exception --> global handler handle
    }

}
