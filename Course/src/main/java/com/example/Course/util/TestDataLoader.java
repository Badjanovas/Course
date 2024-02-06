package com.example.Course.util;

import com.example.Course.model.Student;
import com.example.Course.model.Teacher;
import com.example.Course.repository.StudentRepository;
import com.example.Course.repository.TeacherRepository;
import com.example.Course.service.EmailSendingService;
import com.example.Course.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataLoader implements CommandLineRunner {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final EmailSendingService emailSendingService;


    @Override
    public void run(String... args) throws Exception{

        // Create Teachers
        Teacher teacher1 = new Teacher( "John", "Doe", 120, LocalDate.of(1980, 5, 15), "john.doe@example.com", "1234567890", new ArrayList<>());
        Teacher teacher2 = new Teacher( "Jane", "Smith", 95, LocalDate.of(1985, 7, 20), "jane.smith@example.com", "0987654321", new ArrayList<>());

       teacherRepository.saveAll(Arrays.asList(teacher1,teacher2));

        // Create Students for Teacher 1
        List<Student> studentsForTeacher1 = List.of(
                new Student( "AtudentA", "One", LocalDate.of(2000, 1, 1), "studenta@example.com", "1111111111", teacher1),
                new Student("AtudentB", "Two", LocalDate.of(2001, 2, 2), "studentb@example.com", "2222222222", teacher1),
                new Student( "BtudentC", "Three", LocalDate.of(2002, 3, 3), "studentc@example.com", "3333333333", teacher1)
        );

        // Create Students for Teacher 2
        List<Student> studentsForTeacher2 = List.of(
                new Student( "StudentD", "Four", LocalDate.of(2003, 4, 4), "studentd@example.com", "4444444444", teacher2),
                new Student( "StudentE", "Five", LocalDate.of(2004, 5, 5), "studente@example.com", "5555555555", teacher2),
                new Student( "StudentF", "Six", LocalDate.of(2005, 6, 6), "studentf@example.com", "6666666666", teacher2)
        );
        // Save students to the database
        studentRepository.saveAll(studentsForTeacher1);
        studentRepository.saveAll(studentsForTeacher2);

        teacher1.setStudents(studentsForTeacher1);
        teacher2.setStudents(studentsForTeacher2);
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        //emailSendingService.sendEmailWithAttachment("abadjanovas@gmail.com", "Hello", "Message from java.");
        //emailSendingService.sendEmail("abadjanovas@gmail.com", "Hello", "Message from java.");
    }
}
