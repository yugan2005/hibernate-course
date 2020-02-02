package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;


@RestResource(path = "courseSD")
public interface CourseSDRepository extends JpaRepository<Course, Long> {

  Optional<Course> findByName(String name);

  @Query(name = "course_get_steps_courses")
  List<Course> getCoursesWithSteps();

  @Query(value = "select c from Course c where size(c.students) >= 2")
  List<Course> getCourseWithMoreOrEqualTwoStudents();

}
