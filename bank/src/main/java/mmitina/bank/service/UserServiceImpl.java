package mmitina.bank.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import mmitina.bank.DAO.ClientDAO;
import mmitina.bank.DTO.ClientDTO;
import mmitina.bank.DTO.request.LoginRequest;
import mmitina.bank.DTO.request.RegisterRequest;
import mmitina.bank.DTO.response.LoginResponse;
import mmitina.bank.DTO.response.RegisterResponse;
import mmitina.bank.entities.Client;
import mmitina.bank.security.jwt.JwtService;
import mmitina.bank.security.jwt.JwtServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.CredentialException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final ClientDAO clientDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public RegisterResponse registration(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        if (clientDAO.findByEmail(email) != null){
            return new RegisterResponse("Пользователь уже существует");
        }
        Client newClient = Client.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        clientDAO.save(newClient);

        ClientDTO clientDTO = ClientDTO.builder()
                .email(email)
                .build();

        String jwt = jwtService.generateToken(clientDTO);
        return new RegisterResponse(jwt);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws CredentialException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (clientDAO.findByEmail(email) == null){
            throw new RuntimeException("Client not found");
        }

        Client client = clientDAO.findByEmail(email);
        if (!passwordEncoder.matches(password, client.getPassword())){
            throw new CredentialException("Wrong email or password");
        }

        ClientDTO clientDTO = ClientDTO.builder()
                .email(email)
                .build();

        String jwt = jwtService.generateToken(clientDTO);
        return new LoginResponse(jwt);
    }
}
