//package Main.RestController;
//
//import Main.Repository.UserRepository;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import Main.DTO.LoginToken;
//import Main.Model.User;
//import Main.Service.UserService;
//import Main.Utility.ServiceUtility;
//
//@RestController
//@RequestMapping("/login")
//public class LoginRestController {
//
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    ServiceUtility serviceUtility;
//
//    @Autowired
//    UserRepository userRepository;
//
////    public LoginToken toLogin(User user){
////        if(userService.login(user)) {
////            String refreshToken = serviceUtility.generateRefreshToken(user.getUserName());
////            String accessToken = serviceUtility.generateAccessToken(user.getUserName());
////            return new LoginToken(refreshToken, accessToken,"login successfully");
////        }
////
////        return new LoginToken(null ,null, "login failed");
////    }
//
//    @GetMapping("/user")
//    public String userDisplay(@RequestParam String userName){
//        Optional <User> userOptional = userRepository.findById(userName);
//
//        if(userOptional.isPresent()){
//            User user = userOptional.get();
//            return
//                    "username: "  + user.getUserName() + '\n' +
//                    "userpassword: " + user.getUserPassword()+ '\n' +
//                    "userphonenumber :" + user.getUserPhoneNumber();
//        }
//
//        return "not found User";
//    }
//
//
//}
