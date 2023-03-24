/**
 * This class, AStarService, implements the A* search algorithm to find the shortest path between two nodes in a 
 * graph, represented by FlightNodeEntity objects. It takes in a HashMap of UUIDs and FlightNodeEntity objects, 
 * with each FlightNodeEntity object containing the attributes and paths of a node in the graph.
 * 
 * The findPath method performs the A* search algorithm by initializing two HashMaps to keep track of g-scores 
 * and f-scores for each node in the graph. It sets the initial scores for each node to infinity, except for the 
 * starting node, whose g-score is set to 0 and f-score is set to the heuristic function h(start, end). 
 * The method then creates a priority queue of nodes ordered by f-score, with the starting node as the only node in the queue.
 * 
 * The method then loops through the priority queue, dequeuing the node with the lowest f-score and checking its 
 * neighbors. For each neighbor, it computes a tentative g-score and updates the scores if the tentative score 
 * is lower than the current score for that node. If the scores are updated, the neighbor is added to the priority queue.
 * 
 * Once the algorithm reaches the end node, it calls the reconstructPath method to reconstruct the path and 
 * returns it. If the algorithm does not find a path to the end node, it returns null.
 * 
 * The reconstructPath method reconstructs the flight path from the starting node to the ending node using the 
 * "came from" map generated during the A* search algorithm in the findPath method. It starts from the ending 
 * node and follows the links to the previous nodes until the starting node is reached. For each node on the 
 * path, it creates a new Path object between it and the previous node, and adds it as a new relationship to the 
 * node. Finally, it returns an Iterable of FlightNodeEntity objects representing the flight path in the order in 
 * which they should be traversed.
 * 
 * @param nodeSet HashMap with the ID and Node that has the attributes of the node and its paths.
 * @return an Iterable of FlightNodeEntity objects representing the shortest path from start to end or null if no path was found.
 * @throws ResourceNotFoundException if the starting node cannot be found in the graph.
 */
package br.edu.inteli.cc.m5.maverick.services;

import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
import br.edu.inteli.cc.m5.maverick.models.Path;

import java.util.*;
public class AStarService {
    private HashMap<UUID, FlightNodeEntity> graph;
    private Map<UUID, Double> gScore;
    private Map<UUID, Double> fScore;
    private Map<UUID, UUID> cameFrom;

    /**
     * Constructs an AStarService instance with a graph.
     *
     * @param nodeSet a HashMap containing all nodes in the graph.
     */
    public AStarService(HashMap<UUID, FlightNodeEntity> nodeSet) {
        this.graph = nodeSet;
    }

    /**
     * This method performs an A* search algorithm to find the shortest path between two nodes in a graph. It takes in two
     * UUIDs representing the start and end nodes of the path, and returns an Iterable of FlightNodeEntity objects
     * representing the path from start to end.
     *
     * The method initializes two HashMaps to keep track of g-scores and f-scores for each node in the graph. It sets the
     * initial scores for each node to infinity, except for the starting node, whose g-score is set to 0 and f-score is
     * set to the heuristic function h(start, end). The method then creates a priority queue of nodes ordered by f-score,
     * with the starting node as the only node in the queue.
     *
     * The method then loops through the priority queue, dequeuing the node with the lowest f-score and checking its
     * neighbors. For each neighbor, it computes a tentative g-score and updates the scores if the tentative score is lower
     * than the current score for that node. If the scores are updated, the neighbor is added to the priority queue.
     *
     * Once the algorithm reaches the end node, it calls the reconstructPath method to reconstruct the path and returns it.
     * If the algorithm does not find a path to the end node, it returns null.
     *
     * @param start the UUID of the starting node
     * @param end the UUID of the ending node
     * @return an Iterable of FlightNodeEntity objects representing the shortest path from start to end
     *         or null if no path was found
     * @throws ResourceNotFoundException if the starting node cannot be found in the graph
     */
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

                double weight = path.getElevation() + path.getDistance();

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

    /**
     * This method reconstructs the flight path from the starting node to the ending node using the "came from" map
     * generated during the A* search algorithm in the findPath method.
     * It starts from the ending node and follows the links to the previous nodes until the starting node is reached.
     * For each node on the path, it creates a new Path object between it and the previous node, and adds it as a new
     * relationship to the node. Finally, it returns an Iterable of FlightNodeEntity objects representing the flight path
     * in the order in which they should be traversed.
     *
     * @param currentId the ID of the node to start the reconstruction from
     * @return an Iterable of FlightNodeEntity objects representing the reconstructed flight path
     */
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

    /**
     * Calculates the haversine distance between two FlightNodeEntity objects, taking into account their
     * latitude, longitude, and elevation. The haversine distance is the great-circle distance between two
     * points on a sphere (i.e., the Earth).
     * @param node1 The first FlightNodeEntity object.
     * @param node2 The second FlightNodeEntity object.
     * @return The haversine distance between node1 and node2, in meters.
     */
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