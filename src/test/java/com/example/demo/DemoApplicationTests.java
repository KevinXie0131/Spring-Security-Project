package com.example.demo;

import com.myspringsecurity.myapp.MyappApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MyappApplication.class)
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	@WithMockUser(username="jack", password="password", roles={"READER"})
	public void testListWithUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void testListWithAnonymous() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/1"))
				.andExpect(status().is3xxRedirection());
	}

	@Test
	public void testBCryptPasswordEncoder() {

		CharSequence rawPassword = "123456";

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePasswd = encoder.encode(rawPassword);
		boolean isMatch = encoder.matches(rawPassword, encodePasswd);
		System.out.println("encodePasswd:" + encodePasswd);
		System.out.println(isMatch);
	}
}
