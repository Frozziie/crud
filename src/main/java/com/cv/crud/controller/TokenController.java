package com.cv.crud.controller;

import com.cv.crud.dto.LoginDTO;
import com.cv.crud.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TokenController {

    @Autowired
    TokenService tokenService;

    @PostMapping("/token")
    public Object getToken(@RequestBody LoginDTO loginDTO) {
        return tokenService.generateToken(loginDTO);
    }
}
