package com.echoohce.jpa.hibernate.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Course {

  @Id
  @GeneratedValue
  @Setter(AccessLevel.NONE)
  private Long id;

  private String name;

  public Course(String name) {
    this.name = name;
  }
}
