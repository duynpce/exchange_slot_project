package MajorClass;

import Main.Entity.MajorClass;
import Main.Repository.MajorClassRepository;
import Main.Service.MajorClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = {MajorClassService.class, MajorClassRepository.class, MajorClass.class})
@EnableCaching
class MajorClassServiceCacheTest {

    @Autowired
    private MajorClassService majorClassService;

    @Autowired
    private MajorClassRepository majorClassRepository;

//    @MockitoBean
    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setup() {
        // Reset cache before each test
        cacheManager.getCache("majorClassData").clear();
    }

    @Test
    void testCachePut() {
        System.out.println("Running testCachePut...");
        // Tạo một đối tượng MajorClass
        MajorClass majorClass = new MajorClass();
        majorClass.setClassCode("CS101");
        majorClass.setSlot("A1");

        // Gọi phương thức add vào service
        majorClassService.add(majorClass);

        // Kiểm tra cache đã được cập nhật
        Cache cache = cacheManager.getCache("majorClassData");
        assertNotNull(cache.get("CS101"), "Cache should contain the entry");
    }

    @Test
    void testCacheable() {
        System.out.println("Running testCacheable...");
        // Tạo một đối tượng MajorClass
        MajorClass majorClass = new MajorClass();
        majorClass.setClassCode("CS102");
        majorClass.setSlot("B1");

        // Lưu vào database
        majorClassRepository.save(majorClass);

        // Gọi phương thức tìm kiếm bằng classCode để cache vào
        majorClassService.findByClassCode("CS102");

        // Kiểm tra cache đã có dữ liệu
        Cache cache = cacheManager.getCache("majorClassData");
        assertNotNull(cache.get("CS102"), "Cache should contain the entry after calling findByClassCode");
    }

    @Test
    void testCacheEvict() {
        System.out.println("Running testCacheEvict...");
        // Tạo một đối tượng MajorClass
        MajorClass majorClass = new MajorClass();
        majorClass.setClassCode("CS103");
        majorClass.setSlot("C1");

        // Lưu vào database
        majorClassRepository.save(majorClass);

        // Gọi phương thức để cập nhật MajorClass
        majorClass.setSlot("C2");
        majorClassService.update(majorClass);

        // Kiểm tra xem cache có bị xóa đi sau khi cập nhật không
        Cache cache = cacheManager.getCache("majorClassData");
        assertNull(cache.get("CS103"), "Cache should be evicted after update");

        // Lấy lại classCode và kiểm tra cache được cập nhật
        majorClassService.findByClassCode("CS103");
        assertNotNull(cache.get("CS103"), "Cache should be updated after re-fetching the classCode");
    }

}
