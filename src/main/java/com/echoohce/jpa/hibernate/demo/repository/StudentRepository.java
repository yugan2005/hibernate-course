package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.entity.Passport;
import com.echoohce.jpa.hibernate.demo.entity.Student;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@Transactional
public class StudentRepository {

  private final EntityManager em;

  public StudentRepository(EntityManager em) {
    this.em = em;
  }

  public Student findById(Long id) {
    return em.find(Student.class, id);
  }

  public void deleteById(Long id) {
    Student student = findById(id);
    em.remove(student);
  }

  public Student save(Student student) {
    if (student.getId() == null) {
      em.persist(student);
    } else {
      em.merge(student);
    }

    return student;
  }

  public List<Student> findAll() {
    CriteriaQuery<Student> criteriaQuery = em.getCriteriaBuilder().createQuery(Student.class);
    Root<Student> root = criteriaQuery.from(Student.class);
    criteriaQuery = criteriaQuery.select(root);
    TypedQuery<Student> typedQuery = em.createQuery(criteriaQuery);
    return typedQuery.getResultList();
  }

  public List<Student> findAllByName(String name) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
    Root<Student> root = criteriaQuery.from(Student.class);
    Path<String> namePath = root.get("name");
    ParameterExpression<String> nameParameter = criteriaBuilder.parameter(String.class);
    Predicate nameEqualPredicate = criteriaBuilder.equal(namePath, nameParameter);
    criteriaQuery = criteriaQuery.select(root).where(nameEqualPredicate);
    TypedQuery<Student> typedQuery = em.createQuery(criteriaQuery);
    typedQuery.setParameter(nameParameter, name);
    return typedQuery.getResultList();
  }

  public boolean isAttached(Student student) {
    return em.contains(student);
  }

  public void saveStudentWithPassport() {
    Student student = new Student("XZH");
    student = save(student);
    Passport passport = new Passport("B1222");
    passport.setStudent(student);
    em.persist(passport);
  }

  public void addStudentWithCourses(Student student, Course course) {
    course = course.toBuilder().student(student).build();
    student = student.toBuilder().course(course).build();
    em.persist(student);
  }
}
