package Main.Service;

import Main.Entity.MajorClass;
import Main.Exception.BaseException;
import Main.Repository.MajorClassRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MajorClassService {

    private final MajorClassRepository majorClassRepository;

    @Caching(evict = { /// temporarily evict caches when adding new major class
            @CacheEvict(value = "majorClassExists", key = "#majorClass.classCode"),
            @CacheEvict(value = "majorClassData", key = "#majorClass.classCode")
    })
    public void add(MajorClass majorClass){
        majorClassRepository.save(majorClass);
    }

    @CachePut(value = "majorClassData", key = "#majorClass.classCode")
    public void update(MajorClass majorClass){
        majorClassRepository.save(majorClass);
    }

    @Cacheable(value = "majorClassData", key = "#classCode")
    public MajorClass findByClassCode(String classCode){
        return majorClassRepository.findByClassCode(classCode).
                orElseThrow(() -> new BaseException("not found class with class code: " + classCode, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = "majorClassExists", key = "#classCode")
    public boolean existsByClassCode(String classCode){
        return majorClassRepository.existsByClassCode(classCode);
    }
}
