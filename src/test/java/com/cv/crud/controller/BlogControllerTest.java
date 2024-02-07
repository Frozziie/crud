package com.cv.crud.controller;

import com.cv.crud.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class BlogControllerTest {

    /* Para utilizar @Autowired con MockMvc debe estar habilitado la inyección de dependencia en nuestro test */
    @Autowired
    private MockMvc mockMvc;

    /* Se mockea el bean de TokenService ya que es una dependencia requerida por TokenController y al utilizar
    * la anotación @WebMcvTest se escanean todos los controladores por ende nos vemos obligados a proveerlo aun cuando
    * no es explicitamente necesario para este test */
    @MockBean
    private TokenService tokenService;

    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_USER")
    public void givenBlogController_whenCalledWithUserRol_thenReturnMessage() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/blog")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello!"))
                .andReturn();

        String resultString = mvcResult.getResponse().getContentAsString();

        assertNotNull(mvcResult);
        assertEquals("Hello!", resultString); /* another example of "andExpect(content().string("Hello!"))" */
    }
}