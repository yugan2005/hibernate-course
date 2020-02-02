package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.entity.Course_;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@DirtiesContext
class CourseSDRepositoryTest {

  @Autowired
  private CourseSDRepository courseSDRepository;

  @Test
  void testFind() {
    log.info("10001 course -> {}", courseSDRepository.findById(10001L).orElse(null));
    log.info("total number of courses -> {}", courseSDRepository.count());
  }

  @Test
  void save() {
    String courseName = "unit test course";
    Course newCourse = new Course(courseName);
    Course savedCourse  = courseSDRepository.save(newCourse);
    assertEquals(newCourse, savedCourse);

    String updatedName = "updated unit test course";
    savedCourse.setName(updatedName);

    Course findCourse = courseSDRepository.findById(savedCourse.getId()).orElse(null);
    assertNotEquals(findCourse, savedCourse);
    assertNotEquals(findCourse.getName(), savedCourse.getName());

    courseSDRepository.save(savedCourse);
    Course find2Course = courseSDRepository.findById(findCourse.getId()).orElse(null);
    assertNotEquals(findCourse, find2Course);
    assertNotEquals(findCourse.getName(), find2Course.getName());

    assertEquals(savedCourse.getName(), find2Course.getName());
  }

  @Test
  void sort() {
    Sort sort = Sort.by(Sort.Direction.ASC, Course_.NAME);
    log.info("sorted courses -> {}", courseSDRepository.findAll(sort));
  }

  @Test
  void pagination() {
    PageRequest pageRequest = PageRequest.of(0, 2);

    Page<Course> firstPage = courseSDRepository.findAll(pageRequest);
    Pageable secondPageable = firstPage.nextPageable();
    final Page<Course> secondPage = courseSDRepository.findAll(secondPageable);

    log.info("the first page -> {}", firstPage.getContent());
    log.info("the second page -> {}", secondPage.getContent());
  }

  @Test
  void customizedMethod() {
    String courseName = "Hibernate in 50 steps";
    log.info("course with Name -> {}", courseSDRepository.findByName(courseName).orElse(null));

    String nonExistName = "not exist";
    assertFalse(courseSDRepository.findByName(nonExistName).isPresent());

    log.info("courses with steps: -> {}", courseSDRepository.getCoursesWithSteps());

    log.info("courses with >= 2 students -> {}", courseSDRepository.getCourseWithMoreOrEqualTwoStudents());
  }
}