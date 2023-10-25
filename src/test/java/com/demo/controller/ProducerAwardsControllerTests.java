package com.demo.controller;

import com.demo.service.ProducerAwardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProducerAwardsControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProducerAwardService producerAwardService;
	private static final String URI = "/api/v1/producers/awards";

	@Test
	public void shouldFetchProducersAndAwardsInterval() throws Exception {
		mockMvc.perform(get(URI))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("min.[0].producer", is("Joel Silver")))
				.andExpect(jsonPath("min.[0].previousWin", is(1990)))
				.andExpect(jsonPath("min.[0].followingWin", is(1991)))
				.andExpect(jsonPath("min.[0].intervals", is(1)))
				.andExpect(jsonPath("max.[0].producer", is("Matthew Vaughn")))
				.andExpect(jsonPath("max.[0].previousWin", is(2002)))
				.andExpect(jsonPath("max.[0].followingWin", is(2015)))
				.andExpect(jsonPath("max.[0].intervals", is(13)));
	}
}