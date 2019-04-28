package ru.mail.krivonos.al.service.converter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mail.krivonos.al.repository.model.User;
import ru.mail.krivonos.al.service.converter.impl.UserConverterImpl;
import ru.mail.krivonos.al.service.model.UserDTO;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {

    @Mock
    private RoleConverter roleConverter;

    private UserConverter userConverter;

    @Before
    public void init() {
        userConverter = new UserConverterImpl(roleConverter);
    }

    @Test
    public void shouldReturnUserWithSameID() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        User user = userConverter.fromDTO(userDTO);
        assertEquals(userDTO.getId(), user.getId());
    }

    @Test
    public void shouldReturnUserDTOWithSameID() {
        User user = new User();
        user.setId(1L);
        UserDTO userDTO = userConverter.toDTO(user);
        assertEquals(user.getId(), userDTO.getId());
    }

    @Test
    public void shouldReturnUserWithSameUsername() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        User user = userConverter.fromDTO(userDTO);
        assertEquals(userDTO.getUsername(), user.getUsername());
    }

    @Test
    public void shouldReturnUserDTOWithSameUsername() {
        User user = new User();
        user.setUsername("username");
        UserDTO userDTO = userConverter.toDTO(user);
        assertEquals(user.getUsername(), userDTO.getUsername());
    }

    @Test
    public void shouldReturnUserWithSamePassword() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        User user = userConverter.fromDTO(userDTO);
        assertEquals(userDTO.getPassword(), user.getPassword());
    }

    @Test
    public void shouldReturnUserDTOWithSamePassword() {
        User user = new User();
        user.setPassword("password");
        UserDTO userDTO = userConverter.toDTO(user);
        assertEquals(user.getPassword(), userDTO.getPassword());
    }
}
