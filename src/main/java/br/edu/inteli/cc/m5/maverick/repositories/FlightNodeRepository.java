package br.edu.inteli.cc.m5.maverick.repositories;

import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
import org.springframework.data.repository.CrudRepository;

// Database repository
public interface FlightNodeRepository extends CrudRepository<FlightNodeEntity, Long> {
}