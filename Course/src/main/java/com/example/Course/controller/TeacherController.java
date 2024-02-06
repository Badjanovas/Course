package com.example.Course.controller;

import com.example.Course.exeption.*;
import com.example.Course.model.Student;
import com.example.Course.model.Teacher;
import com.example.Course.service.EmailSendingService;
import com.example.Course.service.PdfService;
import com.example.Course.service.StudentService;
import com.example.Course.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final PdfService pdfService;
    private final EmailSendingService emailSendingService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() throws NoTeachersFoundException {
            return ResponseEntity.status(HttpStatus.OK).body(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) throws NotValidIdException, NoTeachersFoundException {
            return ResponseEntity.status(HttpStatus.OK).body(teacherService.findById(id));
    }

    @GetMapping("/experienced/{experience}")
    public ResponseEntity<?> findByExperience(@PathVariable final Integer experience) throws NotValidExperienceException, NoTeachersFoundException {
            return ResponseEntity.status(HttpStatus.OK).body(teacherService.findTeachersByExperience(experience));
    }

    @PostMapping("/")
    public ResponseEntity<?> addNewTeacher(@RequestBody final Teacher teacher) throws MandatoryFieldException {
            return ResponseEntity.status(HttpStatus.OK).body(teacherService.addNewTeacher(teacher));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable final Long id) throws NotValidIdException, NoTeachersFoundException {
            return ResponseEntity.status(HttpStatus.OK).body(teacherService.deleteTeacher(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable final Long id, @RequestBody final Teacher teacher) throws NotValidIdException, NoTeachersFoundException, SameInformationException {
            return ResponseEntity.status(HttpStatus.OK).body(teacherService.updateTeacherById(id, teacher));
    }

    @GetMapping("/generatepdf/{teacherId}/{studentId}")
    public ResponseEntity<?>  createPdf(@PathVariable final Long teacherId, @PathVariable final Long studentId) throws NotValidIdException, NoTeachersFoundException, NoStudentFoundException, FileNotFoundException, MalformedURLException {
        Teacher teacher = teacherService.findById(teacherId);
        Student student = studentService.findById(studentId);

        pdfService.pdfGenerator(teacher, student);
        return ResponseEntity.status(HttpStatus.OK).body("PDF file created successfully.");
    }
    @GetMapping("/sendinvoice/{email}")
    public ResponseEntity<?> sendInvoice(@PathVariable final String email) throws MessagingException, IOException, NoPdfFileException, IncorrectEmailFormatException {
        emailSendingService.sendEmailWithAttachment(email,"Invoice", "Hello, i am attaching invoice for your studies.");
        return ResponseEntity.status(HttpStatus.OK).body("Email was sent.");
    }
}
