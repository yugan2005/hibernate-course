package com.echoohce.jpa.hibernate.demo;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.entity.Course_;
import com.echoohce.jpa.hibernate.demo.entity.Student;
import com.echoohce.jpa.hibernate.demo.entity.Student_;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class CriteriaQueryTest {

  @Autowired
  private EntityManager em;

  @Test
  void basicSelect() {
    // 1. get the criteriaBuilder
    CriteriaBuilder cb = em.getCriteriaBuilder();

    // 2. get the Criteria Query from builder by specifying the return result type
    CriteriaQuery<Course> criteriaQuery = cb.createQuery(Course.class);

    // 3. define the roots (tables for the from clauses)
    Root<Course> root = criteriaQuery.from(Course.class);
    criteriaQuery = criteriaQuery.select(root);
    // 4. define predicates

    // 5. add the predicates

    // 6. get the TypedQuery
    TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery);

    // 7. get results
    log.info("courses are -> {}", typedQuery.getResultList());
  }

  @Test
  void select100StepsCourses() {
    // 1. get the criteriaBuilder
    CriteriaBuilder cb = em.getCriteriaBuilder();

    // 2. get the Criteria Query from builder by specifying the return result type
    CriteriaQuery<Course> criteriaQuery = cb.createQuery(Course.class);

    // 3. define the roots (tables for the from clauses)
    Root<Course> root = criteriaQuery.from(Course.class);
    criteriaQuery = criteriaQuery.select(root);

    // 4. define predicates
    Predicate like100Steps = cb.like(root.get(Course_.NAME), "%50 step%");

    // 5. add the predicates
    criteriaQuery = criteriaQuery.where(like100Steps);

    // 6. get the TypedQuery
    TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery);

    // 7. get results
    log.info("courses contains '50 step' are -> {}", typedQuery.getResultList());
  }

  @Test
  void isEmpty() {
    // 1. get the criteriaBuilder
    CriteriaBuilder cb = em.getCriteriaBuilder();

    // 2. get the Criteria Query from builder by specifying the return result type
    CriteriaQuery<Course> criteriaQuery = cb.createQuery(Course.class);

    // 3. define the roots (tables for the from clauses)
    Root<Course> root = criteriaQuery.from(Course.class);
    criteriaQuery = criteriaQuery.select(root);

    // 4. define predicates
    Predicate studentEmpty = cb.isEmpty(root.get(Course_.STUDENTS));

    // 5. add the predicates
    criteriaQuery = criteriaQuery.where(studentEmpty);

    // 6. get the TypedQuery
    TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery);

    // 7. get results
    log.info("courses without students -> {}", typedQuery.getResultList());
  }

  @Test
  void join() {
    // 1. get the criteriaBuilder
    CriteriaBuilder cb = em.getCriteriaBuilder();

    // 2. get the Criteria Query from builder by specifying the return result type
    CriteriaQuery<Object[]> criteriaQuery = cb.createQuery(Object[].class);

    // 3. define the roots and joins (tables for the from clauses)
    // select c.name, s.name from Course c inner join c.students s
    Root<Course> coursePath = criteriaQuery.from(Course.class);
    Join<Course, Student> studentPath = coursePath.join(Course_.STUDENTS, JoinType.INNER);
    criteriaQuery = criteriaQuery.multiselect(coursePath.get(Course_.NAME), studentPath.get(Student_.NAME));

    // 4. define predicates

    // 5. add the predicates

    // 6. get the TypedQuery
    TypedQuery<Object[]> typedQuery = em.createQuery(criteriaQuery);

    // 7. get results
    List<Object[]> results = typedQuery.getResultList();
    log.info("course with student list size -> {}", results.size());
    for (Object[] result : results) {
      log.info("course, student -> {}, {}", result[0], result[1]);
    }
  }

  @Test
  void leftJoin() {
    // 1. get the criteriaBuilder
    CriteriaBuilder cb = em.getCriteriaBuilder();

    // 2. get the Criteria Query from builder by specifying the return result type
    CriteriaQuery<Object[]> criteriaQuery = cb.createQuery(Object[].class);

    // 3. define the roots and joins (tables for the from clauses)
    // select c.name, s.name from Course c inner join c.students s
    Root<Course> coursePath = criteriaQuery.from(Course.class);
    Join<Course, Student> studentPath = coursePath.join(Course_.STUDENTS, JoinType.LEFT);
    criteriaQuery = criteriaQuery.multiselect(coursePath.get(Course_.NAME), studentPath.get(Student_.NAME));

    // 4. define predicates

    // 5. add the predicates

    // 6. get the TypedQuery
    TypedQuery<Object[]> typedQuery = em.createQuery(criteriaQuery);

    // 7. get results
    List<Object[]> results = typedQuery.getResultList();
    log.info("course with student list size -> {}", results.size());
    for (Object[] result : results) {
      log.info("course, student -> {}, {}", result[0], result[1]);
    }
  }
}
