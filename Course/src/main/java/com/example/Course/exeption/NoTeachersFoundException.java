package com.example.Course.exeption;

public class NoTeachersFoundException extends Exception{
    public NoTeachersFoundException(final String message){
        super(message);
    }
}
