package ru.mail.krivonos.al.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mail.krivonos.al.repository.model.User;
import ru.mail.krivonos.al.service.converter.RoleConverter;
import ru.mail.krivonos.al.service.converter.UserConverter;
import ru.mail.krivonos.al.service.model.UserDTO;

@Component("userConverter")
public class UserConverterImpl implements UserConverter {

    private final RoleConverter roleConverter;

    @Autowired
    public UserConverterImpl(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(roleConverter.fromDTO(userDTO.getRole()));
        return user;
    }

    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(roleConverter.toDTO(user.getRole()));
        return userDTO;
    }
}
