package br.edu.inteli.cc.m5.maverick.repositories;

import java.util.List;

import br.edu.inteli.cc.m5.maverick.models.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findByName(String name);
    List<Person> findByTeammatesOfName(String name);
}