package Main.Controller;

<<<<<<< HEAD
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class HomeController {


=======
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

    @GetMapping("/hello")
    public String hello(){
        return "hello this is a endpoint from exchange-slot for student of Ngo Phuong Duy and Le Phuoc Duy";
    }
>>>>>>> develop
}
