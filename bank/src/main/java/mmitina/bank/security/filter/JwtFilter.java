package mmitina.bank.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmitina.bank.DAO.ClientDAO;
import mmitina.bank.DTO.ClientDTO;
import mmitina.bank.entities.Client;
import mmitina.bank.security.jwt.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final ClientDAO clientDAO;

    private String getToken(HttpServletRequest request) {

        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("jwt")) {
                    return cookie.getValue();
                }
            }
        }

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            return null;
        }

        final String[] cookieContent = header.split(" ");
        if (cookieContent.length != 2) {
            return null;
        }

        return cookieContent[1].trim();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getToken(request);

        ClientDTO clientDTO = jwtService.parseToken(jwt);

        if(Objects.isNull(clientDTO)) {
            filterChain.doFilter(request, response);
            log.info("no user in jwt");
            return;
        }

        if(clientDAO.findByEmail(clientDTO.getEmail()) == null){
            filterChain.doFilter(request, response);
            log.info("no user in db");
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                clientDTO.getEmail(),
                clientDTO.getPassword(),
                clientDTO.getAuthorities()
        );

        log.info("Create security context");

        SecurityContext context = SecurityContextHolder.getContext();

        context.setAuthentication(authenticationToken);

        log.info("Set authentication");

        filterChain.doFilter(request, response);
    }
}
