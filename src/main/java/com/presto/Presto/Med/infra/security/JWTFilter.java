package com.presto.Presto.Med.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.presto.Presto.Med.infra.exceptions.ApiErrorMessage;
import com.presto.Presto.Med.infra.exceptions.InvalidTokenException;
import com.presto.Presto.Med.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        try{
            if (token != null) {
                    String subject = this.tokenService.validateToken(token);
                    UserDetails user = this.repository.findByEmail(subject);

                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
            }else{
                throw new InvalidTokenException("Invalid Token");
            }
        }catch (InvalidTokenException ex){
            sendErrorResponse(ex, response);
        }

    }

    private String recoverToken(HttpServletRequest req){
        var authHeader = req.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    private void sendErrorResponse (Exception ex, HttpServletResponse response) throws IOException {
        ApiErrorMessage msg = new ApiErrorMessage(ex.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        objectMapper.writeValue(response.getWriter(), msg);
    }
}

