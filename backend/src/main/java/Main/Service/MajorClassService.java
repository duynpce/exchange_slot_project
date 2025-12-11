package Main.Service;

import Main.Entity.MajorClass;
import Main.Enum.IntConstant;
import Main.Exception.BaseException;
import Main.Repository.MajorClassRepository;
import Main.Utility.CacheUtil;
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
    private final int pageSize = IntConstant.CLASS_PAGE_SIZE.getValue();
    private final String cacheData = "majorClassData";
    private final String cacheExists = "majorClassExists";
    private final String cacheListData = "listMajorClassData";

    private final MajorClassRepository majorClassRepository;
    private final CacheUtil<MajorClass> cacheUtil;

    @Caching(
            cacheable = {
                    @Cacheable(value = cacheData, key = "#majorClass.classCode"),
            },
            evict = {
                    @CacheEvict(value = cacheExists, key = "#majorClass.classCode"),
                    @CacheEvict(value = cacheListData, allEntries = true)
            }
    )
    public MajorClass add(MajorClass majorClass) {
        MajorClass savedMajorClass = majorClassRepository.save(majorClass);

        cacheUtil.addOneItemToList(cacheListData, "'all'", majorClass);
        return savedMajorClass;
    }

    @Caching(
            put = {
                    @CachePut(value = cacheData, key = "#majorClass.classCode"),
            },
            evict = {
                    @CacheEvict(value = cacheExists, key = "#majorClass.classCode"),
                    @CacheEvict(value = cacheListData, allEntries = true)
            }
    )
    public MajorClass update(MajorClass majorClass) {
        return majorClassRepository.save(majorClass);
    }

    @Cacheable(value = cacheData, key = "#classCode")
    public MajorClass findByClassCode(String classCode) {
        return majorClassRepository.findByClassCode(classCode)
                .orElseThrow(() -> new BaseException("not found class with class code: " + classCode, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = cacheListData, key = "#slot")
    public List<MajorClass> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<MajorClass> data = majorClassRepository.findBySlot(slot, pageable);

        if (data.isEmpty()) {
            throw new BaseException("no major class found with slot: " + slot, HttpStatus.NOT_FOUND);
        }

        return data;
    }

    @Cacheable(value = cacheExists, key = "#classCode")
    public boolean existsByClassCode(String classCode) {
        return majorClassRepository.existsByClassCode(classCode);
    }

    @Cacheable(value = cacheListData, key = "'all'")
    public List<MajorClass> findAll(int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<MajorClass> data = majorClassRepository.findAll(pageable).stream().toList();

        if (data.isEmpty()) {
            throw new BaseException("no major class found", HttpStatus.NOT_FOUND);
        }
        return data;
    }
}
