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
    public void populateNodes(@RequestParam(required = false, defaultValue = "3") int elevationWeight,
                              @RequestParam(required = false, defaultValue = "1") int distanceWeight,
                              @RequestParam(required = false) List<String> exclusionZones) {
        // Read all nodes from DTED file
        HashMap<UUID, FlightNodeEntity> allNodes = dtedDatabaseService.readPointsFromDataset(elevationWeight, distanceWeight);

        // Filter nodes if exclusion zones are specified
        if (exclusionZones != null) {
            // Filter out nodes that fall within the exclusion zones
            for (String exclusionZone : exclusionZones) {
                String[] coordinates = exclusionZone.split(",");
                Double minLatitude = Double.parseDouble(coordinates[0]);
                Double maxLatitude = Double.parseDouble(coordinates[1]);
                Double minLongitude = Double.parseDouble(coordinates[2]);
                Double maxLongitude = Double.parseDouble(coordinates[3]);

                Iterator<Map.Entry<UUID, FlightNodeEntity>> iterator = allNodes.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<UUID, FlightNodeEntity> entry = iterator.next();
                    Double latitude = entry.getValue().getLatitude();
                    Double longitude = entry.getValue().getLongitude();

                    if (latitude >= minLatitude && latitude <= maxLatitude
                            && longitude >= minLongitude && longitude <= maxLongitude) {
                        iterator.remove(); // Node falls within an exclusion zone
                    }
                }
            }
        }

        this.nodeSet = allNodes;
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

    // Delete - remove all nodes
    @DeleteMapping("/nodes/")
    public ResponseEntity<?> deleteAllNodes() {
        flightNodeRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api")
    public String apiWelcome() {
        return "Welcome to AEL flight path management";
    }

    @GetMapping("/print-nodes")
    public String printNodes() {
        // Create a StringBuilder to store the formatted output
        StringBuilder sb = new StringBuilder();

        // Iterate through the nodeSet and append each node's latitude and longitude
        for (FlightNodeEntity node : nodeSet.values()) {
            sb.append("Node ID: ")
                    .append(node.getId())
                    .append(", Latitude: ")
                    .append(node.getLatitude())
                    .append(", Longitude: ")
                    .append(node.getLongitude())
                    .append("\n");
        }

        // Return the formatted output as a string
        return sb.toString();
    }
}