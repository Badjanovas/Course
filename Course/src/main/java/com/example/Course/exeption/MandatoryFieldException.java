package com.example.Course.exeption;

public class MandatoryFieldException extends Exception {
    public MandatoryFieldException(String message){
        super(message);
    }
}
