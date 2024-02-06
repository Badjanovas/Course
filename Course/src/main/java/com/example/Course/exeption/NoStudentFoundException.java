package com.example.Course.exeption;

public class NoStudentFoundException extends Exception{
    public NoStudentFoundException(final String message) {
        super(message);
    }
}
