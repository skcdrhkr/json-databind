package org.wolf.mapper.model;

import org.wolf.mapper.annotation.JsonEntity;

import java.util.List;

public class Person {

    @JsonEntity
    private int age;
    @JsonEntity
    private String name;
    @JsonEntity
    private List<Person> child;

    public Person(int age, String name, List<Person> child) {
        this.age = age;
        this.name = name;
        this.child = child;
    }

    public List<Person> getChild() {
        return child;
    }

    public void setChild(List<Person> child) {
        this.child = child;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
