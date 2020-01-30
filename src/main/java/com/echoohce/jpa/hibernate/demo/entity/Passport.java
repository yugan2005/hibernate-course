package com.echoohce.jpa.hibernate.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Passport {

  @Id
  @Setter(AccessLevel.NONE)
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String number;

  @ToString.Exclude
  @OneToOne(mappedBy = "passport", fetch = FetchType.LAZY)
  private Student student;

  public Passport(String number) {
    this.number = number;
  }
}
