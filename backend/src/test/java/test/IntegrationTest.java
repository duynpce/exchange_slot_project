package test;


import test.Common.Cleaner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = main.Exchange_Student_Project.class)
@Import(Cleaner.class) // allow to @Autowired Cleaner
@Testcontainers
@Transactional // Rollback after each test -- ensure test isolation
public abstract class IntegrationTest {

    @Autowired
    protected Cleaner cleaner;

    @Autowired
    DataSource dataSource;

    // Initialize mysql container for testing
    @Container // determine this is a container managed by testcontainers
    protected static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.43")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withReuse(true);

    // Set the mysql container properties
    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
//        registry.add("spring.datasource.hikari.connection-timeout", () -> 100000);
//        registry.add("spring.datasource.hikari.max-lifetime", () -> 10000000);
    }

    // Clean up database and cache after each test
    @AfterEach
    void cleanUp() {
        cleaner.clean();
    }

    @Test
    void testConnection() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT 1"))
        {
            assertTrue(rs.next()); assertEquals(1, rs.getInt(1),"database connection test failed");
        }

        System.out.println("Database connection test passed.");
    }


}
