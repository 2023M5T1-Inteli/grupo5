package br.edu.inteli.cc.m5.maverick.controllers;

import br.edu.inteli.cc.m5.maverick.exceptions.ResourceNotFoundException;
import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
import br.edu.inteli.cc.m5.maverick.repositories.FlightNodeRepository;
import br.edu.inteli.cc.m5.maverick.services.AStarService;
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
    public ResponseEntity<Deque<FlightNodeEntity>> getPath() {
        // get a random element from nodeset
        Random rand = new Random();
        int randomIndex = rand.nextInt(this.nodeSet.size());
        FlightNodeEntity start = (FlightNodeEntity) this.nodeSet.values().toArray()[randomIndex];
        // get a different random element from nodeset (do not repeat)
        int randomIndex2 = rand.nextInt(this.nodeSet.size());
        while (randomIndex2 == randomIndex) {
            randomIndex2 = rand.nextInt(this.nodeSet.size());
        }
        FlightNodeEntity end = (FlightNodeEntity) this.nodeSet.values().toArray()[randomIndex2];

        AStarService shortPath = new AStarService(this.nodeSet);
        UUID startId = start.getId();
        UUID endId = end.getId();
        Deque<FlightNodeEntity> paths = (Deque<FlightNodeEntity>) shortPath.findPath(startId, endId);
        flightNodeRepository.saveAll(paths);

        return ResponseEntity.ok(paths);
    }

    // return shortest path between 2 specified nodes
    @GetMapping("/cordPath")
    public ResponseEntity<Deque<FlightNodeEntity>> getCordPath(@RequestParam("sourceLat") double sourceLat,
                                                  @RequestParam("sourceLon") double sourceLon,
                                                  @RequestParam("targetLat") double targetLat,
                                                  @RequestParam("targetLon") double targetLon) {

        AStarService shortPath = new AStarService(this.nodeSet);

        FlightNodeEntity start = null;
        FlightNodeEntity end = null;

        //find nodes corresponding to the parameters
        for (FlightNodeEntity compNode : nodeSet.values()) {
            if (compNode.getLatitude() == sourceLat && compNode.getLongitude() == sourceLon) {
                start = compNode;
            }
            if (compNode.getLatitude() == targetLat && compNode.getLongitude() == targetLon) {
                end = compNode;
            }
        }

        UUID startId = start.getId();
        UUID endId = end.getId();
        Deque<FlightNodeEntity> paths = (Deque<FlightNodeEntity>) shortPath.findPath(startId, endId);
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