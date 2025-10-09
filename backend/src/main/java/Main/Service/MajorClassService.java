package Main.Service;

import Main.Model.Enity.MajorClass;
import Main.Repository.MajorClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MajorClassService {

    @Autowired
    MajorClassRepository majorClassRepository;

    public Optional<MajorClass> findByClassCode(String classCode){
        return majorClassRepository.findByClassCode(classCode);
    }
}
