package Main.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "This is home";
    }

    @GetMapping("/test-access-denied-handler")
    public String accessDeniedHandler(){
        return "you're a admin";
    }

}
