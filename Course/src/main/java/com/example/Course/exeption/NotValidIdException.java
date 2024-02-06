package com.example.Course.exeption;

public class NotValidIdException extends Exception{
    public NotValidIdException(final String message) {
        super(message);
    }
}
