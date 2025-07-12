package com.ia.alexander.controller;

import com.ia.alexander.dto.auth.AuthLoginDto;
import com.ia.alexander.dto.auth.AuthResponseDto;
import com.ia.alexander.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login (@RequestBody AuthLoginDto authLoginDto){
        return ResponseEntity.ok(userDetailsService.login(authLoginDto));
    }

}
