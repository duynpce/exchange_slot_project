package Main.Service;

import Main.Entity.MajorClass;
import Main.Enum.Constant;
import Main.Exception.BaseException;
import Main.Repository.MajorClassRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MajorClassService {

    private final int pageSize = Constant.DefaultPageSize.getPageSize();

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

    @Cacheable(value = "majorClassData", key = "'all'")
    public List<MajorClass> findAll(int page){
        Pageable pageable = PageRequest.of(page, pageSize);

        List<MajorClass> data = majorClassRepository.findAll(pageable).stream().toList();


        if(data.isEmpty()) {
            throw new BaseException("no major class found", HttpStatus.NOT_FOUND);

        }
        return data;
    }
}
