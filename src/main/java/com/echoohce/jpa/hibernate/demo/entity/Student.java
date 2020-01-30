package com.echoohce.jpa.hibernate.demo.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
  private Integer id;

  @Column(nullable = false)
  private String name;

  @OneToOne(fetch = FetchType.LAZY)
  private Passport passport;

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
  public Student(Integer id, String name, Passport passport, @Singular Set<Course> courses) {
    this.id = id;
    this.name = name;
    this.passport = passport;
    this.courses = courses;
  }

  public Student(String name, Passport passport) {
    this.name = name;
    this.passport = passport;
  }

  public void removeCourse(Course course) {
    courses.remove(course);
  }
}
