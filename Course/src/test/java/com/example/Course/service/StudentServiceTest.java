package com.example.Course.service;

import com.example.Course.exeption.MandatoryFieldException;
import com.example.Course.exeption.NoStudentFoundException;
import com.example.Course.exeption.NotValidIdException;
import com.example.Course.exeption.SameInformationException;
import com.example.Course.model.Student;
import com.example.Course.model.Teacher;
import com.example.Course.repository.StudentRepository;
import com.example.Course.validator.GlobalValidator;
import com.example.Course.validator.StudentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentValidator studentValidator;
    @Mock
    private GlobalValidator globalValidator;
    @InjectMocks
    private StudentService studentService;

    @Test
    void getStudentsWithLastNameFirstLetterS() throws NoStudentFoundException {

        Student student1 = new Student("Student", "Student", null, null, null, new Teacher());
        Student student2 = new Student("Student", "Studentas", null, null, null, new Teacher());
        Student student3 = new Student("Student", "notStudent", null, null, null, new Teacher());

        var students = Arrays.asList(student1, student2, student3);

        Mockito.when(studentRepository.findAll()).thenReturn(students);

        var studentsWithLastNameS = studentService.findStudentsByLastNameStartingLetter("s");

        assertEquals(2, studentsWithLastNameS.size());
        assertEquals("Studentas", studentsWithLastNameS.get(1).getLastName());
    }

    @Test
    void updateStudentName() throws SameInformationException, MandatoryFieldException, NotValidIdException, NoStudentFoundException {
        Student student1 = new Student(1L, "Student", "Student", null, null, null, new Teacher());
        Student student2 = new Student(2L, "Student", "Studentas", null, null, null, new Teacher());
        Student student3 = new Student(3L, "Student", "notStudent", null, null, null, new Teacher());

        var students = Arrays.asList(student1, student2, student3);

        Mockito.when(studentRepository.findById(2L)).thenReturn(Optional.ofNullable(students.get(1)));

        Student update = new Student("update", null, null, null, null, null);

        var studentToUpdate = studentService.updateStudentById(2L, update);

        assertEquals("update", studentToUpdate.getFirstName());
    }
}
