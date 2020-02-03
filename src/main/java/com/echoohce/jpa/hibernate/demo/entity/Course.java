package com.echoohce.jpa.hibernate.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Slf4j
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@NamedQueries(value = {@NamedQuery(name = "course_get_all_courses", query = "select c from Course c"),
    @NamedQuery(name = "course_get_steps_courses", query = "select c from Course c where c.name like '%steps'"),
    @NamedQuery(name = "course_get_all_courses_with_students",
        query = "select c from Course c join fetch c.students as s")
})
@Cacheable
@SQLDelete(sql = "UPDATE course AS c SET c.is_deleted=true where c.id=?")
@Where(clause = "is_deleted=false")
public class Course {

  @Id
  @GeneratedValue
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(nullable = false)
  private String name;

  @UpdateTimestamp
  @ToString.Exclude
  private LocalDateTime lastUpdatedTime;

  @CreationTimestamp
  @ToString.Exclude
  @JsonIgnore
  private LocalDateTime createdTime;

  @OneToMany(mappedBy = "course")
  @Setter(AccessLevel.NONE)
  @ToString.Exclude
  private Set<Review> reviews;

  @ManyToMany(mappedBy = "courses")
  @ToString.Exclude
  @Setter(AccessLevel.NONE)
  private Set<Student> students;

  private boolean isDeleted = false;

  @PreRemove
  private void setIsDeletedFlag() {
    log.info("set the course deleted flag to true");
    isDeleted = true;
  }

  public Course(String name) {
    this.name = name;
  }

  @Builder(toBuilder = true)
  public Course(Long id, String name, LocalDateTime lastUpdatedTime, LocalDateTime createdTime,
      @Singular Set<Review> reviews, @Singular Set<Student> students) {
    this.id = id;
    this.name = name;
    this.lastUpdatedTime = lastUpdatedTime;
    this.createdTime = createdTime;
    this.reviews = reviews;
    this.students = students;
  }

  public void removeReview(Review review) {
    reviews.remove(review);
  }

  public void removeStudent(Student student) {
    students.remove(student);
  }
}
