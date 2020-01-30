package com.echoohce.jpa.hibernate.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Review {

  @Id
  @Setter(AccessLevel.NONE)
  @GeneratedValue
  private Long Id;

  private String rating;
  private String description;

  @ManyToOne
  private Course course;

  public Review(String rating, String description, Course course) {
    this.rating = rating;
    this.description = description;
    this.course = course;
  }

  public Review(String rating, String description) {
    this.rating = rating;
    this.description = description;
  }
}
