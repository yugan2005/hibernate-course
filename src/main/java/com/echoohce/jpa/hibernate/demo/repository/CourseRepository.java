package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class CourseRepository {

  private final EntityManager em;

  public CourseRepository(EntityManager em) {
    this.em = em;
  }

  public Course findById(Long id) {
    return em.find(Course.class, id);
  }

  public void deleteById(Long id) {
    Course course = findById(id);
    em.remove(course);
  }

  public Course save(Course course) {
    if (course.getId() == null) {
      em.persist(course);
    } else {
      em.merge(course);
    }

    return course;
  }

  public List<Course> findAll() {
    CriteriaQuery<Course> criteriaQuery = em.getCriteriaBuilder().createQuery(Course.class);
    Root<Course> root = criteriaQuery.from(Course.class);
    criteriaQuery = criteriaQuery.select(root);
    TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery);
    return typedQuery.getResultList();
  }

  public List<Course> findByName(String name) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
    Root<Course> root = criteriaQuery.from(Course.class);
    Path<String> namePath = root.get("name");
    ParameterExpression<String> nameParameter = criteriaBuilder.parameter(String.class);
    Predicate nameEqualPredicate = criteriaBuilder.equal(namePath, nameParameter);
    criteriaQuery = criteriaQuery.select(root).where(nameEqualPredicate);
    TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery);
    typedQuery.setParameter(nameParameter, name);
    return typedQuery.getResultList();
  }
}
