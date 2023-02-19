package br.edu.inteli.cc.m5.maverick.repositories;

import br.edu.inteli.cc.m5.maverick.models.FlightPathNode;
import br.edu.inteli.cc.m5.maverick.models.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FlightPathNodeRepository extends Neo4jRepository<FlightPathNode, Long> {

}
