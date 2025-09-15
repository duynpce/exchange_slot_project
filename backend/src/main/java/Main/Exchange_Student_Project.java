package Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"Main", "Main.Service","Main.RestController","Main.Repository"})
/// componentScan --> allow external class to scan and use
public class Exchange_Student_Project {

	public static void main(String[] args) {
		SpringApplication.run(Exchange_Student_Project.class, args);
	}

}
