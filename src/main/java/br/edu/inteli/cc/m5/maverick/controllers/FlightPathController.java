package br.edu.inteli.cc.m5.maverick.controllers;

import br.edu.inteli.cc.m5.maverick.exceptions.ResourceNotFoundException;
import br.edu.inteli.cc.m5.maverick.models.FlightPathNode;
import br.edu.inteli.cc.m5.maverick.models.Path;
import br.edu.inteli.cc.m5.maverick.repositories.FlightPathNodeRepository;
import br.edu.inteli.cc.m5.maverick.repositories.PathRepository;
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

    private final PathRepository pathRepository;

    // Controller constructor
    public FlightPathController(DTEDDatabaseService dtedDatabaseService, FlightPathNodeRepository flightPathNodeRepository, PathRepository pathRepository) throws Exception {
        this.dtedDatabaseService = dtedDatabaseService;
        this.flightPathNodeRepository = flightPathNodeRepository;
        this.pathRepository = pathRepository;
    }

    // Create - create all nodes in database from DTED file simplification
    @PostMapping("/nodes")
    public void populateNodes() {
        dtedDatabaseService.readPointsFromDataset();
        System.out.println("111");

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

    @PostMapping("/relationship")
    public void relateNodes() {
        FlightPathNode[] nodes = new FlightPathNode[300];
        int i = 0;
        for (FlightPathNode node : flightPathNodeRepository.findAll()) {
            if (i >= 255) {
                System.out.println("111");
                break;
            }
            nodes[i] = node;
            i++;
        }
        for (int j = 0; j < nodes.length; j++) {
            if (j == nodes.length - 1) {
//                Path path = new Path(nodes[0], nodes[j].getElevation(), nodes[j].getLatitude(), nodes[j].getLongitude());
//                nodes[j].setGoesTo(path);
                break;
            }
            // Relationship create
            Path path = new Path(nodes[j+1], nodes[j].getElevation(), nodes[j].getLatitude(), nodes[j].getLongitude());
            pathRepository.save(path);
//            System.out.println("222");
//            nodes[j].setGoesTo(path);
//            flightPathNodeRepository.save(nodes[j]);
        }
    }

    @GetMapping("/api")
    public String apiWelcome() {
        return "Welcome to AEL flight path management";
    }
}
