package com.javaguides.todomanagement.service;

import com.javaguides.todomanagement.dto.JwtAuthResponseDto;
import com.javaguides.todomanagement.dto.LoginDto;
import com.javaguides.todomanagement.dto.RegisterDto;
import com.javaguides.todomanagement.exception.TodoAPIException;

public interface AuthService {
    String register(RegisterDto registerDto) throws TodoAPIException;
    JwtAuthResponseDto login(LoginDto loginDto) throws TodoAPIException;
}
