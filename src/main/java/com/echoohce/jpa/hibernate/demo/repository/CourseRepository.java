package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.entity.Course;
import com.echoohce.jpa.hibernate.demo.entity.Review;
import java.util.Collection;
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
    log.info("before delete the isDeleted flag is -> {}", course.isDeleted());
    em.remove(course);
    log.info("after delete the isDeleted flag is -> {}", course.isDeleted());
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

  public List<Course> findAllByName(String name) {
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

  public boolean isAttached(Course course) {
    return em.contains(course);
  }

  public void playWithEM() {
    Course course = new Course("Play with EM");
    save(course);
    log.info("Added course is in Persist context? {}", isAttached(course));
    em.flush();

    Course course2 = new Course("React within 100 steps");
    save(course2);
    em.flush();

    em.detach(course2);

    course.setName("Play with EM - updated");
    course2.setName("React within 100 steps - updated");

    Course updatedCourse = findById(10001L);
    updatedCourse.setName("updated time should change");
  }

  public void addReviewsToCourseByCourseId(Long courseId, Collection<Review> reviews) {
    Course course = findById(courseId);
    course = course.toBuilder().reviews(reviews).build();
    for (Review review : reviews) {
      review.setCourse(course);
      em.persist(review);
    }
  }

  public void addReviewToCourseByCourseId(Long courseId, Review review) {
    Course course = findById(courseId);
    course = course.toBuilder().review(review).build();
    review.setCourse(course);
    em.persist(review);
  }
}
