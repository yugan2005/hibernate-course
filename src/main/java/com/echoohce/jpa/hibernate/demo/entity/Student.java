package com.echoohce.jpa.hibernate.demo.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Student {

  @Id
  @GeneratedValue
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(
      name = "student_course_jointable",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id")
  )
  @Setter(AccessLevel.NONE)
  @ToString.Exclude
  private Set<Course> courses;

  @Builder(toBuilder = true)
  public Student(Long id, String name, @Singular Set<Course> courses) {
    this.id = id;
    this.name = name;
    this.courses = courses;
  }

  public Student(String name) {
    this.name = name;
  }

  public void removeCourse(Course course) {
    courses.remove(course);
  }
}
