package com.echoohce.jpa.hibernate.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public abstract class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Setter(AccessLevel.NONE)
  private Long id;

  private String name;

  public Employee(String name) {
    this.name = name;
  }
}
