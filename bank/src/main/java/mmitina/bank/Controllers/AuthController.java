package mmitina.bank.Controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import mmitina.bank.DTO.response.RegisterResponse;
import mmitina.bank.DTO.response.LoginResponse;
import mmitina.bank.DTO.request.LoginRequest;
import mmitina.bank.DTO.request.RegisterRequest;
import mmitina.bank.service.UserService;
import mmitina.bank.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse servletResponse) throws CredentialException {
        LoginResponse loginResponse = userService.login(loginRequest);
        Cookie cookie = new Cookie("jwt", loginResponse.getJwt());
        cookie.setPath("/api/v1");
        servletResponse.addCookie(cookie);
        return loginResponse;
    }
    @PostMapping("/registration")
    public RegisterResponse registration(@RequestBody RegisterRequest registerRequest){
        return userService.registration(registerRequest);
    }
}
