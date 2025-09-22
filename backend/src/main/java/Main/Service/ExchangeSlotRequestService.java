package Main.Service;


import Main.DTO.ExchangeClassRequestDTO;
import Main.DTO.ResponseDTO;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Model.Enity.ExchangeSlotRequest;
import Main.Repository.ExchangeSlotRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public List<ExchangeSlotRequest> findBySlot(String slot){
        return exchangeSlotRequestRepository.findBySlot(slot);
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

    public boolean deleteById(int id){
        exchangeSlotRequestRepository.deleteById(id);

        return true;
    }
}
