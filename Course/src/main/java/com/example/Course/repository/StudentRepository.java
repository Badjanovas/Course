package com.example.Course.repository;

import com.example.Course.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM student WHERE vardas LIKE ?1%", nativeQuery = true)
    List<Student> findByFirstNameStartingWith(String letter);
}
