package gogo.com.gogo_kan.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import gogo.com.gogo_kan.response.UnAuthorized;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt  implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        System.out.println(authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        UnAuthorized body = new UnAuthorized();
        body.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        body.setType("Unauthorized");
        body.setMessage(request.getServletPath());
        body.setPath(request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

    }
}
