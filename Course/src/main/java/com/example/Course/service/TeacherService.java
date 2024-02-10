package com.example.Course.service;

import com.example.Course.exeption.*;
import com.example.Course.model.Teacher;
import com.example.Course.repository.TeacherRepository;
import com.example.Course.validator.GlobalValidator;
import com.example.Course.validator.TeacherValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherValidator teacherValidator;
    private final GlobalValidator globalValidator;

    public List<Teacher> getAllTeachers() throws NoTeachersFoundException {
        log.info("Looking for teachers.");
        final var teachers = teacherRepository.findAll();
        teacherValidator.exceptionEmptyTeacherList(teachers);

        log.info(teachers.size() + " teachers were found in DB.");
        return teachers;
    }

    public Teacher findById(final Long id) throws NoTeachersFoundException, NotValidIdException {
        globalValidator.exceptionNotValidId(id);
        log.info("Looking for teacher with " + id + " id number.");
        final var teacher = teacherRepository.findById(id);
        teacherValidator.exceptionEmptyTeacherObject(teacher, id);
        log.info(teacher.get().getFirstName() + " " + teacher.get().getLastName() + " was found in DB by id number: " + id + ".");

        return teacher.get();
    }

    public List<Teacher> findTeachersByExperience(final Integer experience) throws NoTeachersFoundException, NotValidExperienceException {
        log.info("Looking for teachers that have more experience than " + experience + " months");
        teacherValidator.exceptionExperienceLessThanZero(experience);
        final var teachers = teacherRepository.findAll().stream()
                .filter(teacher -> teacher.getExperienceInMonths() > experience)
                .collect(Collectors.toList());
        teacherValidator.exceptionEmptyTeacherList(teachers);

        log.info(teachers.size() + " teachers where found in DB with experience higher than " + experience + " months.");
        return teachers;
    }

    public List<Teacher> addNewTeacher(final Teacher teacher) throws MandatoryFieldException {
        teacherValidator.exceptionMandatoryFieldsEmpty(teacher);
        teacherRepository.save(teacher);

        log.info(teacher.getFirstName() + " " + teacher.getLastName() + " was created and added to DB successfully. ");
        return teacherRepository.findAll();
    }

    public List<Teacher> deleteTeacher(final Long id) throws NoTeachersFoundException, NotValidIdException {
        globalValidator.exceptionNotValidId(id);
        final var teacher = teacherRepository.findById(id);
        teacherValidator.exceptionEmptyTeacherObject(teacher, id);
        teacherRepository.deleteById(id);

        log.info(teacher.get().getFirstName() + " " + teacher.get().getLastName() + " was deleted from DB successfully.");
        return teacherRepository.findAll();

    }

    public Teacher updateTeacherById(final Long id, final Teacher teacher) throws NoTeachersFoundException, NotValidIdException, SameInformationException {
        globalValidator.exceptionNotValidId(id);
        Teacher teacherToUpdate = teacherRepository.findById(id)
                .orElseThrow(() -> new NoTeachersFoundException("Teacher with " + id + " id number doesn't exist."));

        final boolean isUpdated = updateTeacherIfChanged(teacher, teacherToUpdate);

        if (isUpdated) {
            teacherRepository.save(teacherToUpdate);
            log.info("Teacher with id number " + teacher.getId() + " was updated successfully.");
        } else {
            globalValidator.exceptionSameInformationProvided();
        }
        return teacherToUpdate;
    }

    private boolean updateTeacherIfChanged(final Teacher teacher, final Teacher teacherToUpdate) {
        boolean isUpdated = false;

        if (!Objects.equals(teacherToUpdate.getFirstName(), teacher.getFirstName())) {
            teacherToUpdate.setFirstName(teacher.getFirstName());
            isUpdated = true;
        }
        if (!Objects.equals(teacherToUpdate.getLastName(), teacher.getLastName())) {
            teacherToUpdate.setLastName(teacher.getLastName());
            isUpdated = true;
        }
        if (!Objects.equals(teacherToUpdate.getDob(), teacher.getDob())) {
            teacherToUpdate.setDob(teacher.getDob());
            isUpdated = true;
        }
        if (!Objects.equals(teacherToUpdate.getEmail(), teacher.getEmail())) {
            teacherToUpdate.setEmail(teacher.getEmail());
            isUpdated = true;
        }
        if (!Objects.equals(teacherToUpdate.getExperienceInMonths(), teacher.getExperienceInMonths())) {
            teacherToUpdate.setExperienceInMonths(teacher.getExperienceInMonths());
            isUpdated = true;
        }
        if (!Objects.equals(teacherToUpdate.getPhoneNumber(), teacher.getPhoneNumber())) {
            teacherToUpdate.setPhoneNumber(teacher.getPhoneNumber());
            isUpdated = true;
        }
        return isUpdated;
    }
}
