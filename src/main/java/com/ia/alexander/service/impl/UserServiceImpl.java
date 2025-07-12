package com.ia.alexander.service.impl;

import com.ia.alexander.dto.user.UserRegisterDto;
import com.ia.alexander.dto.user.UserResponseDto;
import com.ia.alexander.entity.UserSec;
import com.ia.alexander.exception.ResourceNotFoundException;
import com.ia.alexander.mapper.UserMapper;
import com.ia.alexander.repository.UserSecRepository;
import com.ia.alexander.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserSecRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponseDto save(UserRegisterDto userRegisterDto) {
        UserSec user = userMapper.toEntity(userRegisterDto);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        UserSec userSaved = userRepository.save(user);
        return userMapper.toResponseDto(userSaved);
    }

    @Override
    public UserResponseDto findById(Long userId) {
        UserSec user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User")
        );
        return userMapper.toResponseDto(user);
    }



}
