package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.entity.Address;
import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.entity.Passport;
import com.echoohce.jpa.hibernate.demo.entity.Student;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@DirtiesContext
public class StudentRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private EntityManager entityManager;

  @Test
  @Transactional
  void getStudentWithPassport() {
    Student student = entityManager.find(Student.class, 40001L);
    log.info("student info -> {}", student);
    log.info("passport info -> {}", entityManager.find(Passport.class, student.getId()));
  }

  @Test
  @Transactional
  void getPasswordWithStudent() {
    Passport passport = entityManager.find(Passport.class, 40003L);
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

    Student student = entityManager.find(Student.class, 40004L);
    log.info("student -> {}", student);
    log.info("courses of the student -> {}", student.getCourses());
  }

  @Test
  void addAddressToStudent() {
    Student student = studentRepository.findById(40002L);
    String city = "mountain view";
    String line = "1023 Dale Ave";
    String zipcode = "94040";
    Address address = Address.builder().addressCity(city).addressLine(line).
        addressZipcode(zipcode).build();
    student.setAddress(address);
    Student savedStudent = studentRepository.save(student);
    assertEquals(savedStudent, student);
    log.info("Student address is -> {}", savedStudent.getAddress());
  }

}
