package Main.Service;

import Main.Entity.MajorClass;
import Main.Exception.BaseException;
import Main.Repository.MajorClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MajorClassService {

    @Autowired
    MajorClassRepository majorClassRepository;

    public void add(MajorClass majorClass){
        majorClassRepository.save(majorClass);
    }

    public void update(MajorClass majorClass){
        majorClassRepository.save(majorClass);
    }

    public MajorClass findByClassCode(String classCode){
        return majorClassRepository.findByClassCode(classCode).
                orElseThrow(() -> new BaseException("not found class with class code: " + classCode, HttpStatus.NOT_FOUND));
    }

    public boolean existsByClassCode(String classCode){
        return majorClassRepository.existsByClassCode(classCode);
    }
}
