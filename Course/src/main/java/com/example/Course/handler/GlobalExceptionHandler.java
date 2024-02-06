package com.example.Course.handler;

import com.example.Course.exeption.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoTeachersFoundException.class)
    public ResponseEntity<Object> handleNoTeachersFoundException(NoTeachersFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MandatoryFieldException.class)
    public ResponseEntity<Object> handleMandatoryFieldException(MandatoryFieldException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidIdException.class)
    public ResponseEntity<Object> handleNotValidIdException(NotValidIdException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidExperienceException.class)
    public ResponseEntity<Object> handleNotValidExperienceException(NotValidExperienceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameInformationException.class)
    public ResponseEntity<Object> handleSameInformationException(SameInformationException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception e) {
        return new ResponseEntity<>("An unexpected error has occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoStudentFoundException.class)
    public ResponseEntity<Object> handleNoStudentFoundException(NoStudentFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoPdfFileException.class)
    public ResponseEntity<Object> handleNoPdfFileException(NoPdfFileException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IncorrectEmailFormatException.class)
    public ResponseEntity<Object> handleIncorrectEmailFormatException(IncorrectEmailFormatException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
