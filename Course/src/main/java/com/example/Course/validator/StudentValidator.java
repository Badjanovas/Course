package com.example.Course.validator;

import com.example.Course.exeption.MandatoryFieldException;
import com.example.Course.exeption.NoStudentFoundException;
import com.example.Course.exeption.NotValidIdException;
import com.example.Course.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentValidator {
    public void exceptionEmptyStudentList(final List<Student> students, String costumeMessage) throws NoStudentFoundException {
        if (students.isEmpty()) {
            log.error("No students were found in DB!" + costumeMessage);
            throw new NoStudentFoundException("No students were found in DB!" + costumeMessage);
        }
    }

    public void exceptionEmptyStudentObject(final Optional<Student> student, final Long id) throws NoStudentFoundException {
        if (student.isEmpty()) {
            log.error("Student with id number: " + id + " was not found!");
            throw new NoStudentFoundException("Student with id number: " + id + " was not found!");
        }
    }

    public void exceptionMandatoryFieldsEmpty(final Student student) throws MandatoryFieldException {
        if (student.getFirstName() == null || student.getLastName() == null || student.getTeacher() == null){
            log.error("Some of mandatory fields are empty. New student wasn't created!");
            throw new MandatoryFieldException("Some of mandatory fields are empty. New student wasn't created!");
        }
    }
}
