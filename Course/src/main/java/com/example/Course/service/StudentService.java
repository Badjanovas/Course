package com.example.Course.service;

import com.example.Course.exeption.MandatoryFieldException;
import com.example.Course.exeption.NoStudentFoundException;
import com.example.Course.exeption.NotValidIdException;
import com.example.Course.exeption.SameInformationException;
import com.example.Course.model.Student;
import com.example.Course.repository.StudentRepository;
import com.example.Course.validator.GlobalValidator;
import com.example.Course.validator.StudentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentValidator studentValidator;
    private final GlobalValidator globalValidator;

    public List<Student> getAllStudents() throws NoStudentFoundException {
        log.info("Looking for students.");
        final var students = studentRepository.findAll();
        studentValidator.exceptionEmptyStudentList(students, null);

        log.info(students.size() + " students were found in DB.");
        return students;
    }

    public Student findById(final Long id) throws NotValidIdException, NoStudentFoundException {
        globalValidator.exceptionNotValidId(id);
        log.info("Looking for student with id number " + id + ".");
        final var student = studentRepository.findById(id);
        studentValidator.exceptionEmptyStudentObject(student, id);
        log.info(student.get().getFirstName() + student.get().getLastName() + " was found id DB by id number: " + id + ".");

        return student.get();
    }

    public List<Student> addNewStudent(final Student student) throws MandatoryFieldException {
        studentValidator.exceptionMandatoryFieldsEmpty(student);
        studentRepository.save(student);

        log.info(student.getFirstName() + student.getLastName() + " was created and added to DB successfully.");
        return studentRepository.findAll();
    }

    public List<Student> deleteStudent(final Long id) throws NotValidIdException, NoStudentFoundException {
        globalValidator.exceptionNotValidId(id);
        final var student = studentRepository.findById(id);
        studentValidator.exceptionEmptyStudentObject(student, id);
        studentRepository.deleteById(id);

        log.info(student.get().getFirstName() + " " + student.get().getLastName() + " was deleted from DB successfully.");
        return studentRepository.findAll();
    }

    public Student updateStudentById(final Long id, final Student student) throws NotValidIdException, SameInformationException, MandatoryFieldException, NoStudentFoundException {
        globalValidator.exceptionNotValidId(id);
        Student studentToUpdate = studentRepository.findById(id)
                .orElseThrow(() -> new NoStudentFoundException("Student with " + id + " id number doesn't exist."));


        final boolean isUpdated = updateStudentIfChanged(student, studentToUpdate);

        if (isUpdated) {
            studentRepository.save(studentToUpdate);
            log.info("Student with id number " + student.getId() + " was updated successfully.");
        } else {
            globalValidator.exceptionSameInformationProvided();
        }

        return studentToUpdate;
    }

    private boolean updateStudentIfChanged(final Student student, final Student studentToUpdate) throws MandatoryFieldException {
        boolean isUpdated = false;

        if (!Objects.equals(studentToUpdate.getFirstName(), student.getFirstName())) {
            studentToUpdate.setFirstName(student.getFirstName());
            isUpdated = true;
        }
        if (!Objects.equals(studentToUpdate.getLastName(), student.getLastName())) {
            studentToUpdate.setLastName(student.getLastName());
            isUpdated = true;
        }
        if (!Objects.equals(studentToUpdate.getDob(), student.getDob())) {
            studentToUpdate.setDob(student.getDob());
            isUpdated = true;
        }
        if (!Objects.equals(studentToUpdate.getEmail(), student.getEmail())) {
            studentToUpdate.setEmail(student.getEmail());
            isUpdated = true;
        }
        if (!Objects.equals(studentToUpdate.getPhoneNumber(), student.getPhoneNumber())) {
            studentToUpdate.setPhoneNumber(student.getPhoneNumber());
            isUpdated = true;
        }
        if (!Objects.equals(studentToUpdate.getTeacher(), student.getTeacher())) {
            studentToUpdate.setTeacher(student.getTeacher());
            isUpdated = true;
        }

        return isUpdated;
    }

    /**
     * Retrieves a list of {@link Student} entities whose first name starts with a specified letter.
     * The search is case-insensitive, meaning it will match both uppercase and lowercase starting letters.
     *
     * @param letter The letter with which the first names of the students should start.
     *               The method converts this letter to uppercase to ensure the search is case-insensitive.
     * @return A list of {@link Student} entities matching the criteria. If no students are found,
     * this method throws a {@link NoStudentFoundException}.
     * @throws NoStudentFoundException If no students are found that match the starting letter.
     */
    public List<Student> findStudentsByFirstNameStartingLetter(String letter) throws NoStudentFoundException {
        String upperCase = letter.toUpperCase();
        log.info("Looking for students that first name letter starts with: " + upperCase);
        final List<Student> students = studentRepository.findByFirstNameStartingWith(upperCase);
        studentValidator.exceptionEmptyStudentList(students, null);

        return students;
    }

    public List<Student> findStudentsByLastNameStartingLetter(String letter) throws NoStudentFoundException {
        String upperCase = letter.toUpperCase();
        log.info("Looking for students that last name letter starts with: " + upperCase);
        List<Student> students = studentRepository.findAll();
        studentValidator.exceptionEmptyStudentList(students, null);
        final List<Student> filteredStudents = students.stream()
                .filter(student -> student.getLastName().startsWith(upperCase))
                .collect(Collectors.toList());

        String costumeMessage = " No students were found with first letter " + letter + " in the last name.  ";
        studentValidator.exceptionEmptyStudentList(filteredStudents, costumeMessage);

        return filteredStudents;
    }
}
