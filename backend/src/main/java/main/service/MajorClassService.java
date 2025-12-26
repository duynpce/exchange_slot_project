package main.service;

import main.entity.MajorClass;
import main.constant.IntConstant;
import main.exception.BaseException;
import main.repository.MajorClassRepository;
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
    private final int PAGE_SIZE = IntConstant.CLASS_PAGE_SIZE.getValue();
    private final String CACHE_DATA = "majorClassData";
    private final String CACHE_EXISTS = "majorClassExists";
    private final String CACHE_LIST_DATA = "listMajorClassData";

    private final MajorClassRepository majorClassRepository;

    @Caching(
            put = {
                    @CachePut(value = CACHE_DATA, key = "#majorClass.classCode"),
            },
            evict = {
                    @CacheEvict(value = CACHE_EXISTS, key = "#majorClass.classCode"),
                    @CacheEvict(value = CACHE_LIST_DATA, allEntries = true)
            }
    )
    public MajorClass save(MajorClass majorClass) {
        return majorClassRepository.save(majorClass);
    }

    @Cacheable(value = CACHE_DATA, key = "#classCode")
    public MajorClass findByClassCode(String classCode) {
        return majorClassRepository.findByClassCode(classCode)
                .orElseThrow(() -> new BaseException("not found class with class code: " + classCode, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = CACHE_LIST_DATA, key = "#slot")
    public List<MajorClass> findBySlot(String slot, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<MajorClass> data = majorClassRepository.findBySlot(slot, pageable);

        if (data.isEmpty()) {
            throw new BaseException("no major class found with slot: " + slot, HttpStatus.NOT_FOUND);
        }

        return data;
    }

    @Cacheable(value = CACHE_EXISTS, key = "#classCode")
    public boolean existsByClassCode(String classCode) {
        return majorClassRepository.existsByClassCode(classCode);
    }

    @Cacheable(value = CACHE_LIST_DATA, key = "#page")
    public List<MajorClass> findAll(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<MajorClass> data = majorClassRepository.findAll(pageable).getContent();

        if (data.isEmpty()) {
            throw new BaseException("no major class found", HttpStatus.NOT_FOUND);
        }
        return data;
    }
}
