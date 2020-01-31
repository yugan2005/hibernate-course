package com.echoohce.jpa.hibernate.demo.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ParttimeEmployee extends Employee {
  private BigDecimal hourlyPay;

  public ParttimeEmployee(String name, BigDecimal hourlyPay) {
    super(name);
    this.hourlyPay = hourlyPay;
  }
}
