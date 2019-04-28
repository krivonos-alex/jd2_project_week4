package ru.mail.krivonos.al.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import ru.mail.krivonos.al.service.UserService;
import ru.mail.krivonos.al.service.model.UserDTO;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private Model model;

    private List<UserDTO> users;

    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void init() {
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        users = new ArrayList<>();
        users.add(new UserDTO());
        when(userService.getUsers()).thenReturn(users);
    }


    @Test
    public void shouldReturnUsersPageForUserssGetRequest() {
        String users = userController.getUsers(model);
        Assert.assertEquals("users", users);
    }

    @Test
    public void requestForUsersIsSuccessfullyProcessedWithAvailableUsersList() throws Exception {
        this.mockMvc.perform(get("/users.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", equalTo(users)))
                .andExpect(forwardedUrl("users"));
    }
}
