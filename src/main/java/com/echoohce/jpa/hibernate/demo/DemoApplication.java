package com.echoohce.jpa.hibernate.demo;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.entity.FulltimeEmployee;
import com.echoohce.jpa.hibernate.demo.entity.ParttimeEmployee;
import com.echoohce.jpa.hibernate.demo.entity.Review;
import com.echoohce.jpa.hibernate.demo.entity.ReviewRating;
import com.echoohce.jpa.hibernate.demo.entity.Student;
import com.echoohce.jpa.hibernate.demo.repository.CourseRepository;
import com.echoohce.jpa.hibernate.demo.repository.EmployeeRepository;
import com.echoohce.jpa.hibernate.demo.repository.StudentRepository;
import com.google.common.collect.ImmutableList;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  private final CourseRepository courseRepository;
  private final StudentRepository studentRepository;
  private final EmployeeRepository employeeRepository;

  public DemoApplication(CourseRepository courseRepository, StudentRepository studentRepository,
      EmployeeRepository employeeRepository) {
    this.courseRepository = courseRepository;
    this.studentRepository = studentRepository;
    this.employeeRepository = employeeRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Override
  public void run(String... args) {
    // Find a course
    Course course = courseRepository.findById(10001L);
    log.info("course name is -> {}", course.getName());

    // Add a new course
    String addedCourseName = "Thymeleaf in 75 Steps";
    Course addedCourse = new Course();
    addedCourse.setName(addedCourseName);
    courseRepository.save(addedCourse);

    String differentName = "Thymeleaf in 25 steps";
    addedCourse.setName(differentName);
    log.info("The added course is in the persistent context: {}",
        courseRepository.isAttached(addedCourse));


//    try {
//      courseRepository.playWithEM();
//    } catch (Exception ignored) {
//    }

    studentRepository.saveStudentWithPassport();

    Review review = new Review(ReviewRating.THREE, "I don't like it so much");
    List<Review> reviewList = ImmutableList.of(
        new Review(ReviewRating.TWO, "Really boring stuff"),
        new Review(ReviewRating.FOUR, "It is a useful course"));

    courseRepository.addReviewsToCourseByCourseId(10004L, reviewList);
    courseRepository.addReviewToCourseByCourseId(10002L, review);

    Student student = Student.builder().name("peggy").build();
    studentRepository.addStudentWithCourses(student, addedCourse);

    FulltimeEmployee fulltimeEmployee = new FulltimeEmployee("full time", new BigDecimal("200000.00"));
    ParttimeEmployee parttimeEmployee = new ParttimeEmployee("part time", new BigDecimal("45.00"));
    employeeRepository.saveEmployee(fulltimeEmployee);
    employeeRepository.saveEmployee(parttimeEmployee);
    log.info("employee list -> {}", employeeRepository.listAllEmployees());

    System.out.println("end");
  }
}
