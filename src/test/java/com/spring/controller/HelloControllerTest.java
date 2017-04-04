package com.spring.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



/**
 * 单元测试 controller
 * @author leo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void test() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/hello/adbcd")).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("expect"));
	}
}
