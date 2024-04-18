package org.wolf.mapper.model;

import org.wolf.mapper.annotation.JsonEntity;

import java.util.List;

public class Person {

    @JsonEntity
    private int age;

    @JsonEntity
    private List<Person> children;
    @JsonEntity
    private String name;

    @JsonEntity
    private Person[] relatives;

    public Person(int age, String name, List<Person> children) {
        this.age = age;
        this.name = name;
        this.children = children;
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Person[] getRelatives() {
        return relatives;
    }

    public void setRelatives(Person[] relatives) {
        this.relatives = relatives;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
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
