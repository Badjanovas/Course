package com.example.Course.exeption;

public class IncorrectEmailFormatException extends Exception{
    public IncorrectEmailFormatException(String message) {
        super(message);
    }
}
