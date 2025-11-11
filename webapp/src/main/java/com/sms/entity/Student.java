package com.sms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private Integer semester;


    public Student() {}

    public Student(String name, String course, Integer semester) {
        this.name = name;
        this.course = course;
        this.semester = semester;
    }


    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public Integer getSemester() {
        return semester;
    }


    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    // removed createdAt mapping to avoid schema mismatch with existing database
}
