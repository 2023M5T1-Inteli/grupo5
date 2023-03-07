package br.edu.inteli.cc.m5.maverick.repositories;

import br.edu.inteli.cc.m5.maverick.models.Path;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PathRepository extends Neo4jRepository<Path, Long> {
}
