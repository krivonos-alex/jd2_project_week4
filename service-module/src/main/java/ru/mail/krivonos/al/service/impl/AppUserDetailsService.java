package ru.mail.krivonos.al.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mail.krivonos.al.repository.connection.impl.ConnectionHandlerImpl;
import ru.mail.krivonos.al.service.UserService;
import ru.mail.krivonos.al.service.exceptions.IllegalUsernameStateException;
import ru.mail.krivonos.al.service.model.AppUserPrincipal;
import ru.mail.krivonos.al.service.model.UserDTO;

@Service("userDetailsService")
public class AppUserDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND_ERROR_MESSAGE = "Can't found user.";

    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandlerImpl.class);

    private final UserService userService;

    @Autowired
    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user;
        try {
            user = userService.getUserByUsername(username);
        } catch (IllegalUsernameStateException e) {
            logger.error(e.getMessage(), e);
            throw new UsernameNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE);
        }
        return new AppUserPrincipal(user);
    }
}
