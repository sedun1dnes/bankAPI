package mmitina.bank.security.jwt;

import mmitina.bank.DTO.ClientDTO;
import mmitina.bank.entities.Client;

public interface JwtService {
    String generateToken(ClientDTO client);
    ClientDTO parseToken(String token);

}
