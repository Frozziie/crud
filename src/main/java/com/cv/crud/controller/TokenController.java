package com.cv.crud.controller;

import com.cv.crud.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TokenController {

    @Autowired
    TokenService tokenService;

    @PostMapping("/token")
    public Object getToken(Authentication authentication) {
        log.debug("Token requested from user: {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        log.debug("Token granted: {}", token);
        return token;
    }
}
