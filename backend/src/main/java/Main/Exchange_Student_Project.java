package Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Exchange_Student_Project {

	public static void main(String[] args) {
		SpringApplication.run(Exchange_Student_Project.class, args);
	}

}
