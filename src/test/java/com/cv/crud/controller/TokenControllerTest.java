package com.cv.crud.controller;

import com.cv.crud.dto.LoginDTO;
import com.cv.crud.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* Esto no funciona sino tengo una clase de config donde defina los beans que quiero cargar.
@ContextConfiguration(classes = {ApplicationConfig.class}) */
/* Carga los beans de la aplicación y los controladores al contexto de Spring
@WebAppConfiguration */
@ExtendWith(SpringExtension.class) /* Junit 5 */
@WebMvcTest /* Carga componentes enfocados en Spring MVC */
class TokenControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        webApplicationContext.getServletContext();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /* Test para validar la inicialización de MockMvc y verificar los beans almacenados en el contexto */
    @Test
    public void whenServletContext_checkForTokenController() {
        webApplicationContext.getBeanDefinitionNames(); // Reviso todos los beans cargados en el contexto. (DEBUG)
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
        assertNotNull(webApplicationContext.getBean("tokenController"));
    }

    @Test
    public void givenTokenController_whenGenerateToken_thenReturnStringToken() throws Exception {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("leo.t.gbravo@gmail.com");
        loginDTO.setPassword("casona");

        String body = objectMapper.writeValueAsString(loginDTO);

        when(tokenService.generateToken(any(LoginDTO.class))).thenReturn("fake_token");

        MvcResult mvcResult = this.mockMvc.perform(post("/token")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // Imprime información sobre la solicitud y respuest HTTP en consola.
                .andExpect(status().isOk())
                .andExpect(content().string("fake_token"))
                .andReturn();

        assertNotNull(mvcResult);
        /* another example of "andExpect(content().string("fake_token"))" */
        assertEquals("fake_token", mvcResult.getResponse().getContentAsString());
    }
}