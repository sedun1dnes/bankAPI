package mmitina.bank.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mmitina.bank.DAO.ClientDAO;
import mmitina.bank.DTO.ClientDTO;
import mmitina.bank.entities.Client;
import mmitina.bank.properties.TokenProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final TokenProperties tokenProperties;

    @Override
    public String generateToken(ClientDTO client) {
        return Jwts.builder()
                .setSubject(client.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() * tokenProperties.getTtl()))
                .signWith(SignatureAlgorithm.HS512, generateKey())
                .compact();
    }
    @Override
    public ClientDTO parseToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build();
        if (!parser.isSigned(token)){
            return null;
        }

        Claims claims = parser.parseClaimsJws(token).getBody();
        String email = claims.getSubject();
        return ClientDTO.builder().email(email).build();
    }
    @SneakyThrows
    private Key generateKey(){
        byte[] keyBytes = tokenProperties.getKey().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
