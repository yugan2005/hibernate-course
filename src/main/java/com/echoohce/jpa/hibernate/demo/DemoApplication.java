package com.echoohce.jpa.hibernate.demo;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.entity.Review;
import com.echoohce.jpa.hibernate.demo.entity.Student;
import com.echoohce.jpa.hibernate.demo.repository.CourseRepository;
import com.echoohce.jpa.hibernate.demo.repository.StudentRepository;
import com.google.common.collect.ImmutableList;
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

  public DemoApplication(CourseRepository courseRepository, StudentRepository studentRepository) {
    this.courseRepository = courseRepository;
    this.studentRepository = studentRepository;
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

    Review review = new Review("3", "I don't like it so much");
    List<Review> reviewList = ImmutableList.of(
        new Review("2.5", "Really boring stuff"),
        new Review("4.7", "It is a useful course"));

    courseRepository.addReviewsToCourseByCourseId(10004L, reviewList);
    courseRepository.addReviewToCourseByCourseId(10002L, review);

    Student student = Student.builder().name("peggy").build();
    studentRepository.addStudentWithCourses(student, addedCourse);
    System.out.println("end");
  }
}
