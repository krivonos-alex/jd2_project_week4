package ru.mail.krivonos.al.service.exceptions;

public class ItemServiceException extends RuntimeException {

    public ItemServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
