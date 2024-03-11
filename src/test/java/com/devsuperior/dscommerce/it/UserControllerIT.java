package com.devsuperior.dscommerce.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.dscommerce.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String clientToken, adminToken, invalidToken;

    @BeforeEach
    void setUp() throws Exception {

        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";

        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        invalidToken = adminToken + "xpto"; // Simulates a wrong token
    }

    @Test
    public void getMeShouldReturnUserDTOWhenAdminLogged() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/me").header("Authorization", "Bearer "
                + adminToken).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(2L));
        result.andExpect(jsonPath("$.name").value("Alex Green"));
        result.andExpect(jsonPath("$.email").value("alex@gmail.com"));
        result.andExpect(jsonPath("$.phone").value("977777777"));
        result.andExpect(jsonPath("$.birthDate").value("1987-12-13"));
        result.andExpect(jsonPath("$.roles").exists());
    }

    @Test
    public void getMeShouldReturnUserDTOWhenClientLogged() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/me").header("Authorization", "Bearer "
                + clientToken).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(1L));
        result.andExpect(jsonPath("$.name").value("Maria Brown"));
        result.andExpect(jsonPath("$.email").value("maria@gmail.com"));
        result.andExpect(jsonPath("$.phone").value("988888888"));
        result.andExpect(jsonPath("$.birthDate").value("2001-07-25"));
        result.andExpect(jsonPath("$.roles").exists());
    }

    @Test
    public void getMeShouldReturnUnauthorizedWhenInvalidToken() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/me").header("Authorization", "Bearer "
                + invalidToken).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }
}
