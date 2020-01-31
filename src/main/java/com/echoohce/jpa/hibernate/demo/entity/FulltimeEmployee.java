package com.echoohce.jpa.hibernate.demo.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(callSuper = true)
@Entity
@NoArgsConstructor
public class FulltimeEmployee extends Employee {

  private BigDecimal salary;

  public FulltimeEmployee(String name, BigDecimal salary) {
    super(name);
    this.salary = salary;
  }
}
