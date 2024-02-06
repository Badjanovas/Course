package com.example.Course.controller;

import com.example.Course.exeption.MandatoryFieldException;
import com.example.Course.exeption.NoStudentFoundException;
import com.example.Course.exeption.NotValidIdException;
import com.example.Course.exeption.SameInformationException;
import com.example.Course.model.Student;
import com.example.Course.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() throws NoStudentFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) throws NotValidIdException, NoStudentFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> addNewStudent(@RequestBody final Student student) throws MandatoryFieldException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.addNewStudent(student));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable final Long id) throws NotValidIdException, NoStudentFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.deleteStudent(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable final Long id, @RequestBody final Student student) throws SameInformationException, MandatoryFieldException, NotValidIdException, NoStudentFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudentById(id,student));
    }

    @GetMapping("/find/name/{letter}")
    public ResponseEntity<?> findStudentsByFirstNameLetter(@PathVariable final String letter) throws NoStudentFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findStudentsByFirstNameStartingLetter(letter));
    }

    @GetMapping("/find/lastname/{letter}")
    public ResponseEntity<?> findStudentsByLastNameLetter(@PathVariable final String letter) throws NoStudentFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findStudentsByLastNameStartingLetter(letter));
    }
}
