package com.ia.alexander.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ia.alexander.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwt!=null){
            jwt = jwt.substring(7);
            DecodedJWT decodedJWT = jwtUtil.validateToken(jwt);
            String userName = jwtUtil.extractUsername(decodedJWT);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userName,null,null);
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
        }

        filterChain.doFilter(request,response);
    }

}
