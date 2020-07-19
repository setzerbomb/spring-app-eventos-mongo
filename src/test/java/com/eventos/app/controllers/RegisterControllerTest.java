package com.eventos.app.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJson
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class RegisterControllerTest {

    private MockMvc mvc;

    @Autowired
    public RegisterControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("Can create users")
    @Test
    public void insert() throws Exception {

        String evento = "{\"email\":\"teste@teste\",\"nome\":\"teste\",\"senha\":\"123\",\"confirmacaoSenha\":\"123\"}";
        mvc.perform(MockMvcRequestBuilders
                .post("/register").content(evento)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
