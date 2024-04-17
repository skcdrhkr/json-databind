package org.wolf.mapper;

import org.wolf.mapper.model.Person;

import java.util.List;

public class JsonMapperDriver {
    public static void main(String[] args) throws IllegalAccessException {
        Person jane = new Person(23, "Jane", null);
        Person susan = new Person(35, "Susan", null);
        Person doll = new Person(12, "Doll", List.of(jane, susan));

        List<Person> persons = List.of(jane, susan, doll);

        // Converting List of Persons to Json
        String personJson = EntityMapper.writeValueAsString(persons);
        System.out.println(personJson);
        System.out.println(EntityMapper.writeValueAsString(new Person[]{jane, susan}));
        System.out.println(EntityMapper.writeValueAsString(jane));

        // Converting Json list to List of Persons
//        List<Person> parsedPersons = List.of(EntityMapper.readValue(personJson, Person[].class));
//        System.out.println(parsedPersons);
//
//        // Converting Person object to Json
//        String janeJson = EntityMapper.writeValueAsString(jane);
//        System.out.println(janeJson);
//
//        // Converting Json object to Person
//        Person parsedJane = EntityMapper.readValue(janeJson, Person.class);
//        System.out.println(parsedJane);

    }
}
