package com.ia.alexander.service;

import com.ia.alexander.dto.user.UserRegisterDto;
import com.ia.alexander.dto.user.UserResponseDto;
import com.ia.alexander.entity.UserSec;

import java.util.List;

public interface UserService {

    UserResponseDto save (UserRegisterDto userRegisterDto);

    UserResponseDto findById (Long userId);


}
