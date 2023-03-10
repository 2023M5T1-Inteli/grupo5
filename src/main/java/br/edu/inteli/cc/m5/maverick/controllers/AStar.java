package br.edu.inteli.cc.m5.maverick.controllers;

import br.edu.inteli.cc.m5.maverick.exceptions.ResourceNotFoundException;
import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
import br.edu.inteli.cc.m5.maverick.models.Path;
import br.edu.inteli.cc.m5.maverick.repositories.FlightNodeRepository;

import java.util.*;

public class AStar {
    private Iterable<FlightNodeEntity> graph;
    private Map<FlightNodeEntity, Double> gScore;
    private Map<FlightNodeEntity, Double> fScore;
    private Map<FlightNodeEntity, FlightNodeEntity> cameFrom;
    private FlightNodeRepository flightNodeRepository;

    public AStar(FlightNodeRepository flightNodeRepository) {
        // Initialize the A* algorithm with the flight node repository
        this.flightNodeRepository = flightNodeRepository;
        this.graph = flightNodeRepository.findAll();
    }

    public Iterable<FlightNodeEntity> findPath(FlightNodeEntity start, FlightNodeEntity end) {
        // Initialization
        gScore = new HashMap<>();
        fScore = new HashMap<>();
        cameFrom = new HashMap<>();

        // Set the initial scores to infinity for each node in the graph
        for (FlightNodeEntity node : graph) {
            gScore.put(node, Double.POSITIVE_INFINITY);
            fScore.put(node, Double.POSITIVE_INFINITY);
        }

        // Set the score for the starting node
        gScore.put(start, 0.0);
        fScore.put(start, haversineDistance(start, end));

        // Add the starting node to the open set
        PriorityQueue<FlightNodeEntity> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        openSet.add(start);

        // Loop through the nodes until the end node is reached
        while (!openSet.isEmpty()) {
            FlightNodeEntity current = openSet.poll();

            if (current.equals(end)) {
                // If the end node has been reached, reconstruct the path and return it
                return reconstructPath(end);
            }

            // Check each neighbor of the current node
            for (Path path : current.getPaths()) {
                Long currentId = path.getTargetId();
                FlightNodeEntity neighbor = flightNodeRepository.findById(currentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Node not found"));

                double weight = path.getElevation() + path.getDistance();

                double tentativeGScore = gScore.get(current) + weight;

                // If the tentative g score is better than the current g score for the neighbor, update the scores
                if (tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + haversineDistance(neighbor, end));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        // If no path was found, return null
        return null;
    }

    private Iterable<FlightNodeEntity> reconstructPath(FlightNodeEntity current) {
        // Reconstruct the path using the "came from" map
        List<FlightNodeEntity> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }

        return path;
    }

    public static double haversineDistance(FlightNodeEntity node1, FlightNodeEntity node2) {
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
