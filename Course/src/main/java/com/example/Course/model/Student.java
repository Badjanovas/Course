package com.example.Course.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Studento_id")
    private Long id;
    @Column(name = "Vardas", nullable = false)
    private String firstName;
    @Column(name = "Pavarde", nullable = false)
    private String lastName;
    @Column(name = "Gimimo_data")
    private LocalDate dob;
    @Column(name = "Electroninis_pastas")
    private String email;
    @Column(name = "Telefono_numeris")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "Mokytojo_id")
    @JsonBackReference
    private Teacher teacher;

 public Student(String firstName, String lastName, LocalDate dob, String email, String phoneNumber, Teacher teacher) {
  this.firstName = firstName;
  this.lastName = lastName;
  this.dob = dob;
  this.email = email;
  this.phoneNumber = phoneNumber;
  this.teacher = teacher;
 }
}
