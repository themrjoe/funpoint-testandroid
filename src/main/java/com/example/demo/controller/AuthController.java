package com.example.demo.controller;

import com.example.demo.domain.AuthDto;
import com.example.demo.domain.AuthResponseDto;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping(value = "/android/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody AuthDto authDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
            User user = userService.findByUserName(authDto.getUsername());
            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + authDto.getUsername() + " not found");
            }
            String token = jwtTokenProvider.createToken(authDto.getUsername(), user.getRoles());
            AuthResponseDto responseDto = new AuthResponseDto(user.getUserName(), token, user.getFirstName(), user.getLastName(), user.getEmail());
            return ResponseEntity.ok(responseDto);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping(value = "/android/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void register(@RequestBody User user) {
        userService.register(user);
    }
}
