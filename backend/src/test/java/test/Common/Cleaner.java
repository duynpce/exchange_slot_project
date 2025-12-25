package test.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Cleaner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CacheManager cacheManager;

    public void clean() {
        // truncate(delete all data) all database tables
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.queryForList("""
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema = DATABASE()
        """, String.class).forEach(
                table -> jdbcTemplate.execute("TRUNCATE TABLE " + table)
        );

        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");

        // Clear all caches
        cacheManager.getCacheNames()
                .forEach(name -> cacheManager.getCache(name).clear());
    }
}
