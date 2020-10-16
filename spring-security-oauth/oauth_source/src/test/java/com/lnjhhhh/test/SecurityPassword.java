package com.lnjhhhh.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author lnjhhhh
 * @CreateDate 2020/10/2 20:31
 * @Version 1.0
 */

public class SecurityPassword {

    List<Employee> employeeList = Arrays.asList(
            new Employee("张三",20,1000.0),
            new Employee("李四",21,2000.0),
            new Employee("王五",22,3000.0),
            new Employee("赵六",23,4000.0),
            new Employee("田七",24,5000.0)
    );

    @Test
    public void test1(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123"));
    }


    @Test
    public void test(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Stream<Integer> stream = list.stream();
        stream.filter(num -> num % 2 == 0).forEach(System.out::print);
    }

    @Test
    public void test3(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = list.stream();
        stream.map(num -> num * num)
                .forEach(System.out::println);

    }

    @Test
    public void test4(){
        Stream<Employee> stream = employeeList.stream();
        Integer reduce = stream.map(Employee::getAge)
                .reduce(0, (x, y) -> ++x);
        System.out.println(reduce);
    }

    @Test
    public void test2(){
        LocalDateTime date = LocalDateTime.now();
        System.out.println(date);
        LocalDateTime date2 = LocalDateTime.of(2017, 12, 17, 9, 31, 31, 31);
        System.out.println(date2);

    }

}

class Employee{
    private String name;
    private Integer age;
    private Double salaly;

    public Employee() {
    }

    public Employee(String name, Integer age, Double salaly) {
        this.name = name;
        this.age = age;
        this.salaly = salaly;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", salaly=" + salaly +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalaly() {
        return salaly;
    }

    public void setSalaly(Double salaly) {
        this.salaly = salaly;
    }
}