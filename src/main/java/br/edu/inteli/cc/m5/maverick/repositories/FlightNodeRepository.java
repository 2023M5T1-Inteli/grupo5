package br.edu.inteli.cc.m5.maverick.repositories;

import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

// Database repository
public interface FlightNodeRepository extends CrudRepository<FlightNodeEntity, Long> {
}
