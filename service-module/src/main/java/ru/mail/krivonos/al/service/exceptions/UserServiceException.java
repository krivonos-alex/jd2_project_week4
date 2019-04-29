package ru.mail.krivonos.al.service.exceptions;

public class UserServiceException extends RuntimeException {

    public UserServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
