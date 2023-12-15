package mmitina.bank.service;

import mmitina.bank.DTO.request.LoginRequest;
import mmitina.bank.DTO.request.RegisterRequest;
import mmitina.bank.DTO.response.LoginResponse;
import mmitina.bank.DTO.response.RegisterResponse;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;

public interface UserService {
    RegisterResponse registration(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest) throws CredentialException;
}
