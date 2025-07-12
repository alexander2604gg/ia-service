package com.ia.alexander.service.impl;

import com.ia.alexander.dto.auth.AuthLoginDto;
import com.ia.alexander.dto.auth.AuthResponseDto;
import com.ia.alexander.entity.UserSec;
import com.ia.alexander.repository.UserSecRepository;
import com.ia.alexander.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserSecRepository userSecRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserSec userSec = userSecRepository.findUserSecByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: ".concat(email))
        );
        return new User(userSec.getEmail() , userSec.getPassword() , List.of());
    }

    public AuthResponseDto login (AuthLoginDto authLoginDto){
        Authentication authentication = authenticate(authLoginDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.createToken(authentication);
        return new AuthResponseDto(authLoginDto.email(),jwt);
    }

    public Authentication authenticate (AuthLoginDto authLoginDto) {
        UserDetails userDetails = loadUserByUsername(authLoginDto.email());
        if (!passwordEncoder.matches(authLoginDto.password(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return new UsernamePasswordAuthenticationToken(authLoginDto.email(),userDetails.getPassword(),userDetails.getAuthorities());
    }



}
