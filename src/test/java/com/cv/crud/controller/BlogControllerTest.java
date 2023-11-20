package com.cv.crud.controller;

import com.cv.crud.configuration.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
@Import({SecurityConfig.class})
@AutoConfigureMockMvc
class BlogControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void whenUnauthenticatedThen401() throws Exception {
        mvc.perform(get("/")).andExpect(status().isUnauthorized());
    }

    @Test
    void whenAuthorizedThen200() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }
}