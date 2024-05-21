package com.javaguides.todomanagement.service.impl;

import com.javaguides.todomanagement.dto.JwtAuthResponseDto;
import com.javaguides.todomanagement.dto.LoginDto;
import com.javaguides.todomanagement.dto.RegisterDto;
import com.javaguides.todomanagement.entity.Role;
import com.javaguides.todomanagement.entity.User;
import com.javaguides.todomanagement.exception.TodoAPIException;
import com.javaguides.todomanagement.repository.RoleRepository;
import com.javaguides.todomanagement.repository.UserRepository;
import com.javaguides.todomanagement.security.jwt.JwtTokenProvider;
import com.javaguides.todomanagement.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) throws TodoAPIException {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username is already exist");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email is already exist");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER");
        roles.add(role);

        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public JwtAuthResponseDto login(LoginDto loginDto) throws TodoAPIException {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(),
                    loginDto.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);


            String roleName = null;
            Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Optional<Role> optionalRole = user.getRoles().stream().findFirst();

                if (optionalRole.isPresent()) {
                    Role userRole = optionalRole.get();
                    roleName = userRole.getName();
                }
            }

            JwtAuthResponseDto jwtAuthResponse = new JwtAuthResponseDto();
            jwtAuthResponse.setAccessToken(jwtTokenProvider.generateToken(authentication));
            jwtAuthResponse.setRole(roleName);

            return jwtAuthResponse;
        } catch (Exception e) {
            throw new TodoAPIException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
