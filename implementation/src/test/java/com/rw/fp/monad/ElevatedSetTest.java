package com.rw.fp.monad;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.util.Set;
import java.util.function.Function;
import com.rw.fp.monad.ElevatedSet;
import org.testng.Assert;
import org.testng.annotations.Test;


enum Company {
  AMAZON, APIGEE, FLIPKART, GOOGLE, ADOBE
}

enum Employee {
  NAVEEN, MOULI, VIKAS, VIVEK, SUBHAJIT, SACHIN
}

public class ElevatedSetTest {

  private static Map<Employee, Employee> managerMap = new HashMap<>();

  static {
    managerMap.put(Employee.NAVEEN, Employee.MOULI);
    managerMap.put(Employee.VIVEK, Employee.MOULI);
    managerMap.put(Employee.VIKAS, Employee.VIVEK);
    managerMap.put(Employee.SUBHAJIT, Employee.SACHIN);
  }

  private static Map<Employee, ElevatedSet<Company>> employmentMap = new HashMap<>();

  static {
    employmentMap.put(Employee.NAVEEN, ElevatedSet.of(Company.ADOBE, Company.APIGEE, Company.FLIPKART));
    employmentMap.put(Employee.VIKAS, ElevatedSet.of(Company.FLIPKART, Company.AMAZON));
    employmentMap.put(Employee.VIVEK, ElevatedSet.of(Company.ADOBE, Company.AMAZON));
    employmentMap.put(Employee.MOULI, ElevatedSet.of(Company.GOOGLE));
  }

  public static Employee manager(Employee employee) {
    return managerMap.get(employee);
  }

  public static ElevatedSet<Company> pastEmployers(Employee employee) {
    return employmentMap.get(employee);
  }

  @Test
  public void testElevatedTest() {
    ElevatedSet<Integer> integerSet = ElevatedSet.of(1, 2, 3);
    Integer sum = integerSet.fold(0, (x, y) -> x + y);
    System.out.println(sum);

    ElevatedSet<Employee> employeeSet = ElevatedSet.of(Employee.NAVEEN, Employee.VIVEK, Employee.VIKAS);

    Assert.assertTrue(employeeSet.map(ElevatedSetTest::manager).get().contains(Employee.VIVEK));
    Assert.assertTrue(employeeSet.map(ElevatedSetTest::manager).get().contains(Employee.MOULI));

    Assert.assertTrue(employeeSet.flatMap(ElevatedSetTest::pastEmployers).get().contains(Company.ADOBE));
    Assert.assertTrue(employeeSet.flatMap(ElevatedSetTest::pastEmployers).get().contains(Company.AMAZON));
    Assert.assertTrue(employeeSet.flatMap(ElevatedSetTest::pastEmployers).get().contains(Company.APIGEE));
    Assert.assertTrue(employeeSet.flatMap(ElevatedSetTest::pastEmployers).get().contains(Company.FLIPKART));
  }
}
