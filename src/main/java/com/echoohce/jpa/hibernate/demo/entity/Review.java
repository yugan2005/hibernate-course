package com.echoohce.jpa.hibernate.demo.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

  @Enumerated(value = EnumType.STRING)
  private ReviewRating rating;

  private String description;

  @ManyToOne
  private Course course;

  public Review(ReviewRating rating, String description, Course course) {
    this.rating = rating;
    this.description = description;
    this.course = course;
  }

  public Review(ReviewRating rating, String description) {
    this.rating = rating;
    this.description = description;
  }
}
