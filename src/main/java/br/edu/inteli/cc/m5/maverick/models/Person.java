package br.edu.inteli.cc.m5.maverick.models;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@Node
public class Person {

    @Id @GeneratedValue private Long id;

    private String name;

    private Person() {
        // Empty constructor required as of Neo4j API 2.0.5
    };

    public Person(String name) {
        this.name = name;
    }

    @Relationship(type = "TEAMMATE", direction = Relationship.Direction.OUTGOING)
    public Set<Person> teammates;

    @Relationship(type = "TEAMMATE", direction = Relationship.Direction.INCOMING)
    public Set<Person> teammatesOf;

    public void worksWith(Person person) {
        if (teammates == null) {
            teammates = new HashSet<>();
        }
        if (person.teammatesOf == null) {
            person.teammatesOf = new HashSet<>();
        }
        teammates.add(person);
        person.teammatesOf.add(this);
    }

    public String toString() {
        Set<Person> allTeammates = new HashSet<>();
        if (teammates != null) {
            allTeammates.addAll(teammates);
        }
        if (teammatesOf != null) {
            allTeammates.addAll(teammatesOf);
        }
        return this.name + "'s teammates => "
                + allTeammates.stream()
                .map(Person::getName)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
