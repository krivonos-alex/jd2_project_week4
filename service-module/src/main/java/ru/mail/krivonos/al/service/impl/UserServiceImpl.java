package ru.mail.krivonos.al.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mail.krivonos.al.repository.UserRepository;
import ru.mail.krivonos.al.repository.connection.ConnectionHandler;
import ru.mail.krivonos.al.repository.exceptions.UserRepositoryException;
import ru.mail.krivonos.al.repository.model.User;
import ru.mail.krivonos.al.service.UserService;
import ru.mail.krivonos.al.service.converter.UserConverter;
import ru.mail.krivonos.al.service.exceptions.ConnectionAutoCloseException;
import ru.mail.krivonos.al.service.exceptions.IllegalUsernameStateException;
import ru.mail.krivonos.al.service.exceptions.UserServiceException;
import ru.mail.krivonos.al.service.model.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final String CONNECTION_CLOSE_ERROR_MESSAGE = "Error while closing connection.";
    private static final String GET_USERS_ERROR_MESSAGE = "Error while getting users from data source.";
    private static final String GET_USER_ERROR_MESSAGE = "Error while getting user info from data source.";
    private static final String ADD_USER_ERROR_MESSAGE = "Error while adding new user into data source.";
    private static final String USERNAME_ERROR_MESSAGE = "User with username \"%s\" already exists.";
    private static final String USERNAME_NOT_FOUND_ERROR_MESSAGE = "User with username \"%s\" is not found.";

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final ConnectionHandler connectionHandler;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserConverter userConverter,
            UserRepository userRepository,
            ConnectionHandler connectionHandler,
            PasswordEncoder passwordEncoder
    ) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.connectionHandler = connectionHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getUsers() {
        try (Connection connection = connectionHandler.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<User> users = userRepository.findUsers(connection);
                List<UserDTO> userDTOList = getUserDTOList(users);
                connection.commit();
                return userDTOList;
            } catch (SQLException | UserRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserServiceException(GET_USERS_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionAutoCloseException(CONNECTION_CLOSE_ERROR_MESSAGE, e);
        }
    }

    @Override
    public UserDTO getUserByUsername(String username) throws IllegalUsernameStateException {
        try (Connection connection = connectionHandler.getConnection()) {
            try {
                connection.setAutoCommit(false);
                User userByUsername = userRepository.findUserByUsername(connection, username);
                if (userByUsername != null) {
                    UserDTO userDTO = userConverter.toDTO(userByUsername);
                    connection.commit();
                    return userDTO;
                } else {
                    throw new IllegalUsernameStateException(USERNAME_NOT_FOUND_ERROR_MESSAGE);
                }

            } catch (SQLException | UserRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserServiceException(GET_USER_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionAutoCloseException(CONNECTION_CLOSE_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void add(UserDTO userDTO) throws IllegalUsernameStateException {
        try (Connection connection = connectionHandler.getConnection()) {
            try {
                connection.setAutoCommit(false);
                if (isUniqueUsername(connection, userDTO.getUsername())) {
                    User user = userConverter.fromDTO(userDTO);
                    user.setPassword(encodePassword(user.getPassword()));
                    userRepository.add(connection, user);
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new IllegalUsernameStateException(String.format(USERNAME_ERROR_MESSAGE,
                            userDTO.getUsername()));
                }
            } catch (SQLException | UserRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserServiceException(ADD_USER_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionAutoCloseException(CONNECTION_CLOSE_ERROR_MESSAGE, e);
        }
    }

    private List<UserDTO> getUserDTOList(List<User> users) {
        return users.stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }

    private boolean isUniqueUsername(Connection connection, String username) throws UserRepositoryException {
        User userByUsername = userRepository.findUserByUsername(connection, username);
        return userByUsername == null;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
