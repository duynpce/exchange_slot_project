package Main;

import Main.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Exchange_Student_Project {

    @Autowired
    AccountService accountService;



	public static void main(String[] args) {
		SpringApplication.run(Exchange_Student_Project.class, args);
	}

}
