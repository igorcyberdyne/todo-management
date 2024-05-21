package com.javaguides.todomanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponseDto {
    private String accessToken;
    private String role;
    private String tokenType = "Bearer";
}
