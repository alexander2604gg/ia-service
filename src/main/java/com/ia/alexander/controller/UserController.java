package com.ia.alexander.controller;

import com.ia.alexander.dto.user.UserRegisterDto;
import com.ia.alexander.dto.user.UserResponseDto;
import com.ia.alexander.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private UserController (UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> save (@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(userService.save(userRegisterDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findById (@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

}
