package com.echoohce.jpa.hibernate.demo;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  private final CourseRepository courseRepository;

  public DemoApplication(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Course course = courseRepository.findById(10001L);
    log.info("course name is -> {}", course.getName());
  }
}
