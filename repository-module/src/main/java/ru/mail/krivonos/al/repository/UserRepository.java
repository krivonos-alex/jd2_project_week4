package ru.mail.krivonos.al.repository;

import ru.mail.krivonos.al.repository.exceptions.UserRepositoryException;
import ru.mail.krivonos.al.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository {

    List<User> findUsers(Connection connection) throws UserRepositoryException;

    User findUserByUsername(Connection connection, String username) throws UserRepositoryException;

    void add(Connection connection, User user) throws UserRepositoryException;
}
