package ru.mail.krivonos.al.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;

public class MainControllerTest {

    private MockMvc mockMvc;

    private MainController mainController;

    @Before
    public void init() {
        mainController = new MainController();
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    public void shouldReturnAboutPageForAboutGetRequest() {
        String homepage = mainController.getHomepage();
        assertEquals("about", homepage);
    }

    @Test
    public void shouldReturnLoginPageForLoginGetRequest() {
        String loginPage = mainController.getLoginPage();
        assertEquals("login", loginPage);
    }

    @Test
    public void shouldReturn403ErrorPageFor403GetRequest() {
        String errorPage = mainController.getErrorPage();
        assertEquals("error/403", errorPage);
    }
}
