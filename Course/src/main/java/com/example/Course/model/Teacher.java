package com.example.Course.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Mokytojo_id")
    private Long id;
    @Column(name = "Vardas", nullable = false)
    private String firstName;
    @Column(name = "Pavarde", nullable = false)
    private String lastName;
    @Column(name = "Patirtis_menesiais")
    private Integer experienceInMonths;
    @Column(name = "Gimimo_data")
    private LocalDate dob;
    @Column(name = "Elektroninis_pastas")
    private String email;
    @Column(name = "Telefono_numeris")
    private String phoneNumber;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> students;

    public Teacher(String firstName, String lastName, Integer experienceInMonths, LocalDate dob, String email, String phoneNumber, List<Student> students) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experienceInMonths = experienceInMonths;
        this.dob = dob;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.students = students;
    }

}
