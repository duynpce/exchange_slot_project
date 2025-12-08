package Main.Controller;

import Main.DTO.Auth.*;
import Main.DTO.Common.ResponseDTO;
import Main.Entity.Account;
import Main.Exception.BaseException;
import Main.Mapper.AccountMapper;
import Main.Service.AuthService;
import Main.Utility.JwtUtil;
import Main.Validator.AuthValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final AuthValidator authValidator;
    private final AccountMapper accountMapper;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        Account account = accountMapper.toEntity(registerRequestDTO);
        authValidator.validateRegister(account);// will put it in service if separate interface

        if(authService.register(account).getId() == 0) {
            throw new BaseException("register failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }



        ResponseDTO<String> responseDTO = new ResponseDTO<>
                (true,"no error", "register successfully",null);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<AccessTokenDTO>> login(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {

        authValidator.validateLogin(loginRequest);// will put it in service if separate interface
        LoginResponseDTO loginResponseDTO = authService.login(loginRequest);

        // Set refresh token in HttpOnly cookie when login successfully
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", loginResponseDTO.getRefreshToken())
                .httpOnly(true)
                .secure(true) /// https
                .sameSite("Strict")
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .build();

        // Add the cookie to the response
        response.addHeader("Set-Cookie", responseCookie.toString());

        ResponseDTO<AccessTokenDTO> responseDTO = new ResponseDTO<>(
                true,
                "no error",
                "login successfully",
                new AccessTokenDTO(loginResponseDTO.getAccessToken())
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<String>> logout(HttpServletResponse response){
        // Clear the refresh token cookie on logout
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true) /// https
                .sameSite("Strict")
                .path("/")
                .maxAge(0) // Set maxAge to 0 to delete the cookie
                .build();

        response.addHeader("Set-Cookie", responseCookie.toString());

        ResponseDTO<String> responseDTO = new ResponseDTO<>
                (true,"no error", "logout successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PatchMapping("/reset_password")
    public ResponseEntity<ResponseDTO<String>> resetPassword
            (@RequestBody ResetPasswordDTO resetPasswordDTO){
        final String username = jwtUtil.getUsername(); ///get username in Context Holder(for security)
        authValidator.validateResetPassword(resetPasswordDTO,username);// will put it in service if separate interface
        authService.resetPassword(resetPasswordDTO,username);

        ResponseDTO<String> responseDTO =
                new ResponseDTO<>(true,"no error","reset successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/refresh_access_token")
    public ResponseEntity<ResponseDTO<AccessTokenDTO>>
    refreshAccessToken(@CookieValue("refreshToken") String refreshToken){
        AccessTokenDTO accessTokenDTO = authService.refreshAccessToken(refreshToken);

        ResponseDTO<AccessTokenDTO> response = new ResponseDTO<>(true,
                "no error", "refresh access token successfully", accessTokenDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/reset_password_for_testing")
    public ResponseEntity<ResponseDTO<String>> resetPasswordForTestTing(@RequestBody ResetPasswordDTO resetPasswordDTO){
        final String username = jwtUtil.getUsername(); ///get username in Context Holder(for security)
        authService.resetPassword(resetPasswordDTO,username);

        ResponseDTO<String> responseDTO =
                new ResponseDTO<>(true,"no error","reset successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
