package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.entity.Passport;
import com.echoohce.jpa.hibernate.demo.entity.Student;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
public class StudentRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private EntityManager entityManager;

  @Test
  @Transactional
  void getStudentWithPassport() {
    Student student = entityManager.find(Student.class, 40001);
    log.info("student info -> {}", student);
    log.info("passport info -> {}", student.getPassport());
  }

  @Test
  @Transactional
  void getPasswordWithStudent() {
    Passport passport = entityManager.find(Passport.class, 20003L);
    log.info("passport info -> {}", passport);
    log.info("student info -> {}", passport.getStudent());
  }

  @Test
  @Transactional
  void retrieveStudentAndCourseManyToMany() {
    Course course = entityManager.find(Course.class, 10001L);
    log.info("course -> {}", course);
    log.info("students of the course -> {}", course.getStudents());

    log.info("The other side");

    Student student = entityManager.find(Student.class, 40004);
    log.info("student -> {}", student);
    log.info("courses of the student -> {}", student.getCourses());
  }

}
