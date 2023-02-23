package br.edu.inteli.cc.m5.maverick.controllers;

import br.edu.inteli.cc.m5.maverick.exceptions.ResourceNotFoundException;
import br.edu.inteli.cc.m5.maverick.models.FlightPathNode;
import br.edu.inteli.cc.m5.maverick.repositories.FlightPathNodeRepository;
import br.edu.inteli.cc.m5.maverick.services.DTEDDatabaseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/flight-path", produces = "application/json")
public class FlightPathController {

    // Database services
    private final DTEDDatabaseService dtedDatabaseService;

    // Database repository
    private final FlightPathNodeRepository flightPathNodeRepository;

    // Controller constructor
    public FlightPathController(DTEDDatabaseService dtedDatabaseService, FlightPathNodeRepository flightPathNodeRepository) throws Exception {
        this.dtedDatabaseService = dtedDatabaseService;
        this.flightPathNodeRepository = flightPathNodeRepository;
    }

    // Create - create all nodes in database from DTED file simplification
    @PostMapping("/nodes")
    public void populateNodes() {
        dtedDatabaseService.readPointsFromDataset();
    }

    // Read - return all nodes from database
    @GetMapping("/nodes")
    public ResponseEntity<List<FlightPathNode>> getNodes() {
        List<FlightPathNode> nodes = flightPathNodeRepository.findAll();
        return ResponseEntity.ok(nodes);
    }

    // Update - update properties from specified node
    @PatchMapping("/nodes/{id}")
    public ResponseEntity<FlightPathNode> updateNode(@PathVariable Long id, @RequestBody FlightPathNode updatedNode) {
        FlightPathNode node = flightPathNodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Node not found"));

        // if updated node property is not null, replace node property

        if (updatedNode.getLatitude() != null) {
            node.setLatitude(updatedNode.getLatitude());
        }

        if (updatedNode.getLongitude() != null) {
            node.setLongitude(updatedNode.getLongitude());
        }

        if (updatedNode.getElevation() != null) {
            node.setElevation(updatedNode.getElevation());
        }

        FlightPathNode savedNode = flightPathNodeRepository.save(node);
        return ResponseEntity.ok(savedNode);
    }

    // Delete - remove specified node
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable Long id) {
        FlightPathNode node = flightPathNodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Node not found"));

        flightPathNodeRepository.delete(node);
        return ResponseEntity.ok().build();
    }
}
