package com.demo;

import com.demo.controller.RestController;
import com.demo.dto.AwardDTO;
import com.demo.model.Award;
import com.demo.service.MovieAwardService;
import org.apache.beam.repackaged.core.org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(RestController.class)
public class ApiMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieAwardService movieAwardService;

    @Test
    public void shouldReturnMoviesAwardsListFromService() throws Exception {
        when(movieAwardService.getAll()).thenReturn(new AwardDTO(new Award(), StringUtils.EMPTY));
        this.mockMvc.perform(get("/api/v1/producers")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("awards")));
    }
}