package com.javaguides.todomanagement.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private Long id;
    private String email;
    private String username;
    private String name;
    private String password;
}
