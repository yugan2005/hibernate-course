package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;


@Repository
public class CourseRepository {

  private final EntityManager em;

  public CourseRepository(EntityManager em) {
    this.em = em;
  }

  public Course findById(Long id) {
    return em.find(Course.class, id);
  }
}
