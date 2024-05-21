package com.javaguides.todomanagement.controller;


import com.javaguides.todomanagement.dto.JwtAuthResponseDto;
import com.javaguides.todomanagement.dto.LoginDto;
import com.javaguides.todomanagement.dto.RegisterDto;
import com.javaguides.todomanagement.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) throws Exception {
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto loginDto) throws Exception {

        JwtAuthResponseDto jwtAuthResponse = authService.login(loginDto);

        return ResponseEntity.ok(jwtAuthResponse);
    }
}
