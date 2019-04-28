package ru.mail.krivonos.al.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static ru.mail.krivonos.al.controller.constant.URLConstants.API_ERROR_403_PAGE_URL;

@RestController
public class MainAPIController {

    @GetMapping(API_ERROR_403_PAGE_URL)
    public ResponseEntity sendForbiddenStatusCode() {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
