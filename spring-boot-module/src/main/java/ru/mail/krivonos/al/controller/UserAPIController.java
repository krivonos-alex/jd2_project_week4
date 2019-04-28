package ru.mail.krivonos.al.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.krivonos.al.service.UserService;
import ru.mail.krivonos.al.service.exceptions.IllegalUsernameStateException;
import ru.mail.krivonos.al.service.model.UserDTO;

import javax.validation.Valid;

import static ru.mail.krivonos.al.controller.constant.URLConstants.API_USERS_PAGE_URL;

@RestController
public class UserAPIController {

    private static final Logger logger = LoggerFactory.getLogger(UserAPIController.class);

    private final UserService userService;

    @Autowired
    public UserAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(API_USERS_PAGE_URL)
    public ResponseEntity saveUser(
            @RequestBody @Valid UserDTO userDTO
    ) {
        try {
            userService.add(userDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalUsernameStateException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
