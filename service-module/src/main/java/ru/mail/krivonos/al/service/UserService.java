package ru.mail.krivonos.al.service;

import ru.mail.krivonos.al.service.exceptions.IllegalUsernameStateException;
import ru.mail.krivonos.al.service.model.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsers();

    UserDTO getUserByUsername(String username) throws IllegalUsernameStateException;

    void add(UserDTO userDTO) throws IllegalUsernameStateException;
}
