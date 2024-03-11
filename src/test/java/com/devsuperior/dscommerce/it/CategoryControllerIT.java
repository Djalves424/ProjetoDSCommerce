package com.devsuperior.dscommerce.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAllShouldReturnListOfCategoryDTO() throws Exception {

        ResultActions result = mockMvc.perform(get("/categories").accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.[0].id").value(1L));
        result.andExpect(jsonPath("$.[0].name").value("Livros"));
        result.andExpect(jsonPath("$.[1].id").value(2L));
        result.andExpect(jsonPath("$.[1].name").value("Eletrônicos"));
        result.andExpect(jsonPath("$.[2].id").value(3L));
        result.andExpect(jsonPath("$.[2].name").value("Computadores"));
    }
}
