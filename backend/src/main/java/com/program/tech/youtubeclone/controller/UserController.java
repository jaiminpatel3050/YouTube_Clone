package com.program.tech.youtubeclone.controller;

import com.program.tech.youtubeclone.dto.CommentDto;
import com.program.tech.youtubeclone.service.UserRegistrationService;
import com.program.tech.youtubeclone.service.UserService;
import com.program.tech.youtubeclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    @GetMapping("/register")
    public String register(Authentication authentication){

        Jwt jwt = (Jwt)authentication.getPrincipal();

        return userRegistrationService.registerUser((jwt.getTokenValue()));

    }

    @PostMapping("/subscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean subscribeUser(@PathVariable String userId){
        userService.subscribeUser(userId);
        return true;
    }

    @PostMapping("/unSubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean unSubscribeUser(@PathVariable String userId){
        userService.unSubscribeUser(userId);
        return true;
    }

    @GetMapping("/{userId}/histroy")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> userHistory(@PathVariable String userId){
        return userService.userHistory(userId);
    }

}
