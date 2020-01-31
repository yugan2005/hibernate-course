package com.echoohce.jpa.hibernate.demo.repository;

import com.echoohce.jpa.hibernate.demo.entity.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class EmployeeRepository {

  private final EntityManager em;

  public EmployeeRepository(EntityManager em) {
    this.em = em;
  }

  public Employee saveEmployee(Employee employee) {
    em.persist(employee);
    return employee;
  }

  public List<Employee> listAllEmployees() {
    return
        em.createQuery("select e from Employee e", Employee.class).getResultList();
  }
}
