package br.edu.inteli.cc.m5.maverick.services;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;



public class AStar {

    private Graph graph;
    private Map<Node, Double> gScore;
    private Map<Node, Double> fScore;
    private Map<Node, Node> cameFrom;

    public AStar(Graph graph) {
        this.graph = graph;
    }

    public List<Node> findPath(Node start, Node end) {

        // Inicialização
        gScore = new HashMap<>();
        fScore = new HashMap<>();
        cameFrom = new HashMap<>();

        for (Node node : graph.getNodes()) {
            gScore.put(node, Double.POSITIVE_INFINITY);
            fScore.put(node, Double.POSITIVE_INFINITY);
        }

        gScore.put(start, 0.0);
        fScore.put(start, graph.haversineDistance(start, end));

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(end)) {
                return reconstructPath(end);
            }

            for (Node neighbor : graph.getNeighbors(current)) {
                if (!graph.hasEdge(current, neighbor)) {
                    continue;
                }

                double tentativeGScore = gScore.get(current) + graph.getEdge(current, neighbor).getWeight();

                if (tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + graph.haversineDistance(neighbor, end));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; // Caminho não encontrado
    }

    private List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }

        return path;
    }

    public String pathToJson(List<Node> path) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode pathJson = JsonNodeFactory.instance.arrayNode();
    
        for (Node node : path) {
            ObjectNode nodeJson = JsonNodeFactory.instance.objectNode();
            nodeJson.put("latitude", node.getLatitude());
            nodeJson.put("longitude", node.getLongitude());
            nodeJson.put("elevation", node.getElevation());
            pathJson.add(nodeJson);
        }
    
        return mapper.writeValueAsString(pathJson);
    }
}