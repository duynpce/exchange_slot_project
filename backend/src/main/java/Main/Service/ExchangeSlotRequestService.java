package Main.Service;

import Main.Model.Enity.ExchangeSlotRequest;
import Main.Repository.ExchangeSlotRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeSlotRequestService {
    
    @Autowired
    ExchangeSlotRequestRepository exchangeSlotRequestRepository;
    
    public List<ExchangeSlotRequest> findByClassCode (String classCode){
        return exchangeSlotRequestRepository.findByClassCode(classCode);
    }

    public boolean add(ExchangeSlotRequest exchangeSlotRequest) {

        try{
            exchangeSlotRequestRepository.save(exchangeSlotRequest);/// save return entity when success, throw exception if failed
        } catch (Exception e) {
            return  false;
        }

        return  true;
    }

    public boolean delete (ExchangeSlotRequest exchangeSlotRequest){////
        try{
            exchangeSlotRequestRepository.deleteById(exchangeSlotRequest.getID());
            return true;
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }
}/// co loi
