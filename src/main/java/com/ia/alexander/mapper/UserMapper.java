package com.ia.alexander.mapper;

import com.ia.alexander.dto.user.UserRegisterDto;
import com.ia.alexander.dto.user.UserResponseDto;
import com.ia.alexander.entity.UserSec;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserSec toEntity (UserRegisterDto userRegisterDto);

    UserResponseDto toResponseDto (UserSec user);

    List<UserResponseDto> toResponseDtoList (List<UserSec> users);

}
