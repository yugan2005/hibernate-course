package com.echoohce.jpa.hibernate.demo;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


@Slf4j
@SpringBootTest(classes = DemoApplication.class)
@DirtiesContext
public class JPQLTests {

  @Autowired
  private EntityManager em;

  @Test
  void likeQueryTest() {
    TypedQuery<Course> query = em.createNamedQuery("course_get_steps_courses", Course.class);
    List<Course> courses = query.getResultList();
    log.info("select c from Course c where c.name like '%steps' -> {}", courses);
  }

  @Test
  void findCourse() {
    log.info("course without student -> {}",
        em.createQuery("select c from Course c where c.students is empty", Course.class).getResultList());
    log.info("course with more than 2 students -> {}",
        em.createQuery("select c from Course c where size(c.students) >= 2", Course.class).getResultList());
    log.info("ordered course list -> {}",
        em.createQuery("select c from Course c order by size( c.students) desc", Course.class).getResultList());
  }

  @Test
  void findStudent() {
    TypedQuery<Object[]> query = em.createQuery(
        "select s.name, p.number from " + "Student s inner join Passport p on s.id = p.id "
            + "where p.number like '%1234%'", Object[].class);

    String result = Arrays.toString(query.getResultList().stream().map(Arrays::toString).toArray());

    log.info("student with passport 1234 -> {}", result);
  }

  @Test
  void join() {
    TypedQuery<Object[]> queryInnerJoin =
        em.createQuery("select c.name, s.name from Student s inner join s.courses c", Object[].class);

    TypedQuery<Object[]> queryOuterJoin =
        em.createQuery("select c.name, s.name from Student s left outer join s.courses c ", Object[].class);

    TypedQuery<Object[]> queryCross =
        em.createQuery("select c.name, s.name from Student s, Course c ", Object[].class);

    log.info("inner join using default method-> {}",
        Arrays.toString(queryInnerJoin.getResultList().stream().map(Arrays::toString).toArray()));
    log.info("inner join using default method-> {}",
        Arrays.toString(queryOuterJoin.getResultList().stream().map(Arrays::toString).toArray()));
    log.info("inner join using default method-> {}",
        Arrays.toString(queryCross.getResultList().stream().map(Arrays::toString).toArray()));
  }
}
