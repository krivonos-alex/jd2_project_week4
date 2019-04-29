package ru.mail.krivonos.al.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ru.mail.krivonos.al.controller.constant.URLConstants.*;

@Controller("mainController")
public class MainController {

    @GetMapping(value = {DEFAULT_PAGE_URL, ABOUT_PAGE_URL})
    public String getHomepage() {
        return "about";
    }

    @GetMapping(LOGIN_PAGE_URL)
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(ERROR_403_PAGE_URL)
    public String getErrorPage() {
        return "error/403";
    }
}
