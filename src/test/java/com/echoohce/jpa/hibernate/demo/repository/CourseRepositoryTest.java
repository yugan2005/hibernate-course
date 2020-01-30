package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.DemoApplication;
import com.echoohce.jpa.hibernate.demo.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = DemoApplication.class)
@DirtiesContext
class CourseRepositoryTest {

  @Autowired
  private CourseRepository courseRepository;

  @Test
  void findById() {
    assertNotNull(courseRepository.findById(10001L));
  }

  @Test
  void deleteById() {
    final Long id = 10005L;
    assertNotNull(courseRepository.findById(id));
    courseRepository.deleteById(id);
    assertNull(courseRepository.findById(id));
  }

  @Test
  @DirtiesContext
  void save() {
    String newCourseName = "Insert new course";
    Course course = new Course();
    course.setName(newCourseName);
    courseRepository.save(course);

    assertTrue(courseRepository.findAll().stream().
        anyMatch(c -> newCourseName.equals(c.getName())));
    Long id = courseRepository.findAllByName(newCourseName).get(0).getId();
    log.info("new course Id -> {}", id);

    Course savedCourse = courseRepository.findById(id);
    String differentName = "Different Name";
    savedCourse.setName(differentName);
    courseRepository.save(savedCourse);
    assertEquals(differentName, courseRepository.findById(id).getName());
  }

  @Test
  @DirtiesContext
  void playWithEM() {
    courseRepository.playWithEM();
  }
}