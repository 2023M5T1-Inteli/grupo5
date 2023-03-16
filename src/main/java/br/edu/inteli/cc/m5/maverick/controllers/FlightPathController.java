package br.edu.inteli.cc.m5.maverick.controllers;

import br.edu.inteli.cc.m5.maverick.exceptions.ResourceNotFoundException;
import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
import br.edu.inteli.cc.m5.maverick.repositories.FlightNodeRepository;
import br.edu.inteli.cc.m5.maverick.services.DTEDDatabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController


@RequestMapping(value = "/flight-path", produces = "application/json")
public class FlightPathController {

    // Database services
    private final DTEDDatabaseService dtedDatabaseService;

    // Database repository
    private final FlightNodeRepository flightNodeRepository;

    private HashMap<UUID, FlightNodeEntity> nodeSet;

    // Controller constructor
    public FlightPathController(DTEDDatabaseService dtedDatabaseService, FlightNodeRepository flightNodeRepository) throws Exception {
        this.dtedDatabaseService = dtedDatabaseService;
        this.flightNodeRepository = flightNodeRepository;
    }

    // GET - return shortest path
    @GetMapping("/path")
    public ResponseEntity<List<FlightNodeEntity>> getPaths() {
        List<FlightNodeEntity> paths = new ArrayList<>();
        AStar shortPath = new AStar(this.nodeSet);
        int i = 0;
        UUID startId = null;
        UUID endId = null;
        for (UUID tmp : nodeSet.keySet()) {
            if (i == 0) {
                startId = tmp;
            }
            if (i == 31) {
                endId = tmp;
            }
            i++;
            System.out.println(tmp);
        }
        for (FlightNodeEntity node : shortPath.findPath(startId,endId)) {
            paths.add(node);
        }

        System.out.println(paths.size());
        flightNodeRepository.saveAll(paths);

        return ResponseEntity.ok(paths);
    }

    // Create - create all nodes in database from DTED file simplification
    @PostMapping("/nodes")
    public void populateNodes() {
        this.nodeSet = dtedDatabaseService.readPointsFromDataset();
    }

    // Read - return all nodes from database
    @GetMapping("/nodes")
    public ResponseEntity<List<FlightNodeEntity>> getNodes() {
        List<FlightNodeEntity> nodes = (List<FlightNodeEntity>) flightNodeRepository.findAll();
        return ResponseEntity.ok(nodes);
    }

    // Update - update properties from specified node
    @PatchMapping("/nodes/{id}")
    public ResponseEntity<FlightNodeEntity> updateNode(@PathVariable Long id, @RequestBody FlightNodeEntity updatedNode) {
        FlightNodeEntity node = flightNodeRepository.findById(id)
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

        FlightNodeEntity savedNode = flightNodeRepository.save(node);
        return ResponseEntity.ok(savedNode);
    }

    // Delete - remove specified node
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable Long id) {
        FlightNodeEntity node = flightNodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Node not found"));

        flightNodeRepository.delete(node);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api")
    public String apiWelcome() {
        return "Welcome to AEL flight path management";
    }
}
