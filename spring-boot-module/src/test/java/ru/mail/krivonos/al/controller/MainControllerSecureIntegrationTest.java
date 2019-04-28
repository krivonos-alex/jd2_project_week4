package ru.mail.krivonos.al.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerSecureIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldSucceedForAboutPage() throws Exception {
        mockMvc.perform(get("/about")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldSucceedForHomePage() throws Exception {
        mockMvc.perform(get("/")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldSucceedForLoginPage() throws Exception {
        mockMvc.perform(get("/login")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldSucceedFor403ErrorPage() throws Exception {
        mockMvc.perform(get("/403")).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
