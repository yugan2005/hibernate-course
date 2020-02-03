package com.echoohce.jpa.hibernate.demo;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.entity.Course_;
import java.util.List;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class NPlusOneProblemTest {

  @Autowired
  private EntityManager em;

  @Test
  @Transactional
  void showNPlusOneProblem() {
    List<Course> courses = em.createNamedQuery("course_get_all_courses", Course.class).getResultList();

    for (Course course : courses) {
      log.info("course -> {}, students -> {}", course, course.getStudents());
    }
  }

  @Test
  @Transactional
  void solveNPlusOneProblemByEntityGraph() {
    TypedQuery<Course> typedQuery = em.createNamedQuery("course_get_all_courses", Course.class);
    EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
    entityGraph.addSubgraph(Course_.STUDENTS);
    List<Course> courses = typedQuery.setHint("javax.persistence.loadgraph", entityGraph).
        getResultList();

    for (Course course : courses) {
      log.info("course -> {}, students -> {}", course, course.getStudents());
    }
  }

  @Test
  @Transactional
  void solveNPlusOneProblemByNativeSql() {
    List<Course> courses = em.createNamedQuery("course_get_all_courses_with_students", Course.class).getResultList();

    for (Course course : courses) {
      log.info("course -> {}, students -> {}", course, course.getStudents());
    }
  }

}
