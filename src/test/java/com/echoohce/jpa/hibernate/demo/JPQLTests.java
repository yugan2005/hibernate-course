package com.echoohce.jpa.hibernate.demo;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest(classes = DemoApplication.class)
public class JPQLTests {

  @Autowired
  private EntityManager em;

  @Test
  void likeQueryTest() {
    TypedQuery<Course> query = em.createNamedQuery("course_get_steps_courses", Course.class);
    List<Course> courses = query.getResultList();
    log.info("select c from Course c where c.name like '%steps' -> {}", courses);
  }
}
