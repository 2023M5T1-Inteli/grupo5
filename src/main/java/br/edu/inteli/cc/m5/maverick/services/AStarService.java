package br.edu.inteli.cc.m5.maverick.services;

import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
import br.edu.inteli.cc.m5.maverick.models.Path;

import java.util.*;

public class AStarService {
    private HashMap<UUID, FlightNodeEntity> graph;
    private Map<UUID, Double> gScore;
    private Map<UUID, Double> fScore;
    private Map<UUID, UUID> cameFrom;

    public AStarService(HashMap<UUID, FlightNodeEntity> nodeSet) {
        this.graph = nodeSet;
    }

    public Iterable<FlightNodeEntity> findPath(UUID start, UUID end) {
        // Initialization
        gScore = new HashMap<>();
        fScore = new HashMap<>();
        cameFrom = new HashMap<UUID, UUID>();

        // Set the initial scores to infinity for each node in the graph
        for (UUID node : graph.keySet()) {
            gScore.put(node, Double.POSITIVE_INFINITY);
            fScore.put(node, Double.POSITIVE_INFINITY);
        }

        // Set the score for the starting node
        gScore.put(start, 0.0);
        fScore.put(start, haversineDistance(graph.get(start), graph.get(end)));

        // Add the starting node to the open set
        PriorityQueue<UUID> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        openSet.add(start);

        // Loop through the nodes until the end node is reached
        while (!openSet.isEmpty()) {
            UUID currentId = openSet.poll();
//            FlightNodeEntity current = flightNodeRepository.findById(currentId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Node not found"));
            FlightNodeEntity current = graph.get(currentId);

            if (currentId.equals(end)) {
                // If the end node has been reached, reconstruct the path and return it
                return reconstructPath(end);
            }

            // Check each neighbor of the current node

            for (Path path : current.getPaths()) {
                UUID pathId = path.getTargetId();
                FlightNodeEntity neighbor = graph.get(pathId);

                // print id, altitude, elevation and weight for node
                double weight = path.getWeight();

                double tentativeGScore = gScore.get(currentId) + weight;

                // If the tentative g score is better than the current g score for the neighbor, update the scores
                if (tentativeGScore < gScore.get(neighbor.getId())) {
                    cameFrom.put(neighbor.getId(), current.getId());
                    gScore.put(neighbor.getId(), tentativeGScore);
                    fScore.put(neighbor.getId(), tentativeGScore + haversineDistance(neighbor, graph.get(end)));

                    if (!openSet.contains(neighbor.getId())) {
                        openSet.add(neighbor.getId());
                    }
                }
            }
        }

        // If no path was found, return null
        return null;
    }

    // helper method for findPath
    private Iterable<FlightNodeEntity> reconstructPath(UUID currentId) {
        // Reconstruct the path using the "came from" map
        Deque<FlightNodeEntity> path = new ArrayDeque<>();
        // Add the first node to the path
        UUID currId = currentId;
        FlightNodeEntity currNode = graph.get(currId);
        // Remove all paths from that node
        currNode.clearAllPaths();
        // Add the current node to the flight path
        path.addFirst(currNode);

        while (cameFrom.containsKey(currId)) {
            // store the previous node id
            UUID prevId = currId;
            FlightNodeEntity prevNode = currNode;
            // update the current node id and node
            currId = cameFrom.get(currId);
            currNode = graph.get(currId);
            // remove all existing relationships
            currNode.clearAllPaths();
            // add an edge from the previous node to the current one
            currNode.addPath(new Path(prevNode, currNode.getLatitude(), currNode.getLongitude(), currNode.getElevation(), currId));
            // add the current node to the start of the list
            path.addFirst(currNode);
        }

        return path;
    }

    // helper method for findPath
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
        return Math.sqrt(distance * distance + elevationDistance * elevationDistance);
    }
}