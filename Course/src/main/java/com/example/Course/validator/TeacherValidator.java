package com.example.Course.validator;

import com.example.Course.exeption.*;
import com.example.Course.model.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TeacherValidator {
    public void exceptionEmptyTeacherList(final List<Teacher> teachers) throws NoTeachersFoundException {
        if (teachers.isEmpty()) {
            log.error("No teachers were found in DB!");
            throw new NoTeachersFoundException("No teachers were found in DB!");
        }
    }

    public void exceptionEmptyTeacherObject(final Optional<Teacher> teacher, final Long id) throws NoTeachersFoundException {
        if (teacher.isEmpty()) {
            log.error("Teacher with id number: " + id + " was not found!");
            throw new NoTeachersFoundException("Teacher with id number: " + id + " was not found!");
        }
    }

    public void exceptionMandatoryFieldsEmpty(final Teacher teacher) throws MandatoryFieldException {
        if (teacher.getFirstName() == null || teacher.getLastName() == null) {
            log.error("Firs name or last name fields are empty. New teacher wasn't created!");
            throw new MandatoryFieldException("Firs name or last name fields are empty. New teacher wasn't created!");
        }
    }

    public void exceptionExperienceLessThanZero(final Integer experience) throws NotValidExperienceException {
        if (experience < 0) {
            log.error("Experience can't be less than 0");
            throw new NotValidExperienceException("Experience can't be less than 0");
        }
    }
}
