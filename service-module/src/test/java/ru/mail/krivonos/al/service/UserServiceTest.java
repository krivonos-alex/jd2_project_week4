package ru.mail.krivonos.al.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mail.krivonos.al.repository.UserRepository;
import ru.mail.krivonos.al.repository.connection.ConnectionHandler;
import ru.mail.krivonos.al.repository.model.User;
import ru.mail.krivonos.al.service.converter.UserConverter;
import ru.mail.krivonos.al.service.exceptions.IllegalUsernameStateException;
import ru.mail.krivonos.al.service.impl.UserServiceImpl;
import ru.mail.krivonos.al.service.model.UserDTO;

import java.sql.Connection;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;
    @Mock
    private ConnectionHandler connectionHandler;
    @Mock
    private Connection connection;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Before
    public void init() {
        userService = new UserServiceImpl(userConverter, userRepository, connectionHandler, passwordEncoder);
    }

    @Test
    public void shouldEncodePassword() throws IllegalUsernameStateException {
        String encodedPassword = "encoded_password";
        UserDTO userDTO = new UserDTO();
        String password = "password";
        userDTO.setPassword(password);
        User user = new User();
        user.setPassword(password);
        when(userConverter.fromDTO(userDTO)).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(connectionHandler.getConnection()).thenReturn(connection);
        userService.add(userDTO);
        Assert.assertEquals(encodedPassword, user.getPassword());
    }
}
