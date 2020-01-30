package com.echoohce.jpa.hibernate.demo;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


@Slf4j
@SpringBootTest(classes = DemoApplication.class)
@DirtiesContext
public class NativeSQLTests {

  @Autowired
  private EntityManager em;

  @Test
  @Transactional
  void likeQueryTest() {
    Query query = em.createNativeQuery("select * from course as c where c.name like '%steps'", Course.class);
    @SuppressWarnings("unchecked cast")
    List<Course> courses = (List<Course>) query.getResultList();
    log.info("select c from Course c where c.name like '%steps' -> {}", courses);

    Query updateQuery = em.createNativeQuery(
        "update course as c set c.last_updated_time = sysdate() where c.name like '%steps'");
    int changedRow = updateQuery.executeUpdate();
    log.info("Updated records -> {}", changedRow);
  }
}
