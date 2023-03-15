package br.edu.inteli.cc.m5.maverick.controllers;

import br.edu.inteli.cc.m5.maverick.exceptions.ResourceNotFoundException;
import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
import br.edu.inteli.cc.m5.maverick.models.Path;
import br.edu.inteli.cc.m5.maverick.repositories.FlightNodeRepository;

import java.util.*;

public class AStar {
    private Iterable<FlightNodeEntity> graph;
    private Map<Long, Double> gScore;
    private Map<Long, Double> fScore;
    private Map<Long, Long> cameFrom;
    private FlightNodeRepository flightNodeRepository;

    public AStar(FlightNodeRepository flightNodeRepository) {
        // Initialize the A* algorithm with the flight node repository
        this.flightNodeRepository = flightNodeRepository;
        this.graph = flightNodeRepository.findAll();
    }

    public Iterable<Long> findPath(FlightNodeEntity start, FlightNodeEntity end) {
        // Initialization
        gScore = new HashMap<>();
        fScore = new HashMap<>();
        cameFrom = new HashMap<>();

        // Set the initial scores to infinity for each node in the graph
        for (FlightNodeEntity node : graph) {
            gScore.put(node.getId(), Double.POSITIVE_INFINITY);
            fScore.put(node.getId(), Double.POSITIVE_INFINITY);
        }

        // Set the score for the starting node
        gScore.put(start.getId(), 0.0);
        fScore.put(start.getId(), haversineDistance(start, end));

        // Add the starting node to the open set
        PriorityQueue<Long> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        openSet.add(start.getId());

        // Loop through the nodes until the end node is reached
        while (!openSet.isEmpty()) {
            Long currentId = openSet.poll();
            FlightNodeEntity current = flightNodeRepository.findById(currentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Node not found"));

            if (currentId.equals(end.getId())) {
                // If the end node has been reached, reconstruct the path and return it
                return reconstructPath(end.getId());
            }

            // Check each neighbor of the current node

            for (Path path : current.getPaths()) {
                Long pathId = path.getTargetId();
                FlightNodeEntity neighbor = flightNodeRepository.findById(pathId)
                        .orElseThrow(() -> new ResourceNotFoundException("Node not found"));

                System.out.println(neighbor);
                System.out.println(neighbor.getId());

                double weight = path.getElevation() + path.getDistance();

                double tentativeGScore = gScore.get(currentId) + weight;

                // If the tentative g score is better than the current g score for the neighbor, update the scores
                if (tentativeGScore < gScore.get(neighbor.getId())) {
                    cameFrom.put(neighbor.getId(), current.getId());
                    gScore.put(neighbor.getId(), tentativeGScore);
                    fScore.put(neighbor.getId(), tentativeGScore + haversineDistance(neighbor, end));

                    if (!openSet.contains(neighbor.getId())) {
                        openSet.add(neighbor.getId());
                    }
                }
            }
        }

        // If no path was found, return null
        System.out.println("NULO");
        return null;
    }

    private Iterable<Long> reconstructPath(Long currentId) {
        // Reconstruct the path using the "came from" map
        List<Long> path = new ArrayList<>();

        path.add(currentId);

        while (cameFrom.containsKey(currentId)) {
            currentId = cameFrom.get(currentId);
            path.add(0, currentId);
        }

        return path;
    }

    public double haversineDistance(FlightNodeEntity node1, FlightNodeEntity node2) {
        // Calculate the haversine distance between two nodes
        final int EARTH_RADIUS = 6371;

            double lat1 = node1.getLatitude();
        double lon1 = node1.getLongitude();
        double alt1 = node1.getElevation();

        double lat2 = node2.getLatitude();
        double lon2 = node2.getLongitude();
        double alt2 = node2.getElevation();

        double dLat = Math.toRadians(Math.abs(lat2 - lat1));
        double dLon = Math.toRadians(Math.abs(lon2 - lon1));
        double dAlt = Math.abs(alt2 - alt1);

        // Haversine formula to calculate the great-circle distance between two points
        // on a sphere (i.e., the Earth).
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000; // Multiply by 1000 to convert to meters
        double elevationDistance = Math.abs(dAlt);

        // Calculate the Euclidean distance between two points in three-dimensional space,
        // taking into account the elevation difference between them.
        return Math.sqrt(distance * distance +elevationDistance *elevationDistance);
    }
}
