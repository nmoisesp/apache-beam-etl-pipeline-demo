package com.demo;

import com.demo.controller.RestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private RestController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}
}