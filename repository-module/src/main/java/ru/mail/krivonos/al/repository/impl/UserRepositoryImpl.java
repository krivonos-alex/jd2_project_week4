package ru.mail.krivonos.al.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.mail.krivonos.al.repository.UserRepository;
import ru.mail.krivonos.al.repository.exceptions.UserRepositoryException;
import ru.mail.krivonos.al.repository.model.Role;
import ru.mail.krivonos.al.repository.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    private static final String USERS_EXTRACTION_ERROR_MESSAGE = "Error while extracting users list.";
    private static final String USER_EXTRACTION_ERROR_MESSAGE = "Error while extracting user.";
    private static final String QUERY_EXECUTION_ERROR_MESSAGE = "Error while executing \"%s\" query.";
    private static final String RESULT_SET_CLOSING_ERROR_MESSAGE = "Error while closing result set.";

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public List<User> findUsers(Connection connection) throws UserRepositoryException {
        String sql = "SELECT u.id, u.username, r.name FROM User u JOIN Role r ON u.role_id = r.id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getUsers(resultSet);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new UserRepositoryException(RESULT_SET_CLOSING_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(String.format(QUERY_EXECUTION_ERROR_MESSAGE, sql), e);
        }
    }

    @Override
    public User findUserByUsername(Connection connection, String username) throws UserRepositoryException {
        String sql = "SELECT u.id, u.username, u.password,  r.name FROM User u JOIN Role r ON u.role_id = r.id " +
                "WHERE u.username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getUser(resultSet);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new UserRepositoryException(RESULT_SET_CLOSING_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(String.format(QUERY_EXECUTION_ERROR_MESSAGE, sql), e);
        }
    }

    @Override
    public void add(Connection connection, User user) throws UserRepositoryException {
        String sql = "INSERT INTO User (username, password, role_id) VALUES (?, ?, (SELECT r.id FROM Role r WHERE name = ?))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().getName());
            int added = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(String.format(QUERY_EXECUTION_ERROR_MESSAGE, sql), e);
        }
    }

    private List<User> getUsers(ResultSet resultSet) throws UserRepositoryException {
        try {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                Role role = new Role();
                role.setName(resultSet.getString("name"));
                user.setRole(role);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(USERS_EXTRACTION_ERROR_MESSAGE, e);
        }
    }

    private User getUser(ResultSet resultSet) throws UserRepositoryException {
        try {
            if (resultSet.next()) {
                User user = new User();
                Role role = new Role();
                role.setName(resultSet.getString("name"));
                user.setRole(role);
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(USER_EXTRACTION_ERROR_MESSAGE, e);
        }
        return null;
    }
}
