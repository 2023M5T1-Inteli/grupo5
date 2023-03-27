
/**
 * This class represents a REST controller for managing flight paths and nodes.
 */
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

    /** 
     * The database service for reading data from DTED files. 
     */
    private final DTEDDatabaseService dtedDatabaseService;

    /** 
     * The repository for managing FlightNodeEntities in the database. 
     */
    private final FlightNodeRepository flightNodeRepository;

    /** 
     * A set of all FlightNodeEntities in the database, keyed by UUID. 
     */
    private HashMap<UUID, FlightNodeEntity> nodeSet;

    /**
     * Creates a new FlightPathController object.
     * 
     * @param dtedDatabaseService - The DTED database service.
     * @param flightNodeRepository - The flight node repository.
     * @throws Exception - If there is an error during initialization.
     */
    public FlightPathController(DTEDDatabaseService dtedDatabaseService, FlightNodeRepository flightNodeRepository) throws Exception {
        this.dtedDatabaseService = dtedDatabaseService;
        this.flightNodeRepository = flightNodeRepository;
    }

    /**
     * Returns the shortest path between two random nodes.
     * 
     * @return The response entity containing the shortest path between two nodes.
     */
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

    /**
     * Returns the shortest path between two specified nodes.
     * 
     * @param sourceLat - The latitude of the source node.
     * @param sourceLon - The longitude of the source node.
     * @param targetLat - The latitude of the target node.
     * @param targetLon - The longitude of the target node.
     * @return The response entity containing the shortest path between the two nodes.
     */
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


    /**
    Create - create all nodes in database from DTED file simplification.
    */
    @PostMapping("/nodes")
    public void populateNodes(@RequestParam(required = false, defaultValue = "3") int elevationWeight,
                              @RequestParam(required = false, defaultValue = "1") int distanceWeight,
                              @RequestParam(required = false) List<String> exclusionZones) {
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

    /**
    * Read - return all nodes from database.
    * 
    @return a ResponseEntity containing a list of FlightNodeEntity objects
    */
    @GetMapping("/nodes")
    public ResponseEntity<List<FlightNodeEntity>> getNodes() {
        List<FlightNodeEntity> nodes = (List<FlightNodeEntity>) flightNodeRepository.findAll();
        return ResponseEntity.ok(nodes);
    }

    /**
    * Update - update properties from specified node.
    * 
    @param id the ID of the node to update
    @param updatedNode the updated node
    @return a ResponseEntity containing the updated FlightNodeEntity object
    @throws ResourceNotFoundException if the specified node is not found
    */
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

    /**
    * Delete - remove specified node.
    * 
    @param id the ID of the node to delete
    @return a ResponseEntity with no content if the deletion was successful
    @throws ResourceNotFoundException if the specified node is not found
    */
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable Long id) {
        FlightNodeEntity node = flightNodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Node not found"));

        flightNodeRepository.delete(node);
        return ResponseEntity.ok().build();
    }

    /**
    * Welcome message for the API.
    *
    @return a String containing the welcome message
    */
    @GetMapping("/api")
    public String apiWelcome() {
        return "Welcome to AEL flight path management";
    }
}