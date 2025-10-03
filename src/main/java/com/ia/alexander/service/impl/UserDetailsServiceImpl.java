package com.ia.alexander.service.impl;

import com.ia.alexander.dto.auth.AuthLoginDto;
import com.ia.alexander.dto.auth.AuthResponseDto;
import com.ia.alexander.entity.UserSec;
import com.ia.alexander.repository.UserSecRepository;
import com.ia.alexander.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl{

    private final UserSecRepository userSecRepository;
    private final JwtUtil jwtUtil;




}
