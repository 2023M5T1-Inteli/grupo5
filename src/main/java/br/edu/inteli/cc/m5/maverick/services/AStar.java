package br.edu.inteli.cc.m5.maverick.services;

import java.nio.file.Path;
import java.util.*;
import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity
import br.edu.inteli.cc.m5.maverick.models.FlightPathNodeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;


public class AStar {

    private List<FlightNodeEntity> graph;
    private Map<FlightNodeEntity, Double> gScore;
    private Map<FlightNodeEntity, Double> fScore;
    private Map<FlightNodeEntity, FlightNodeEntity> cameFrom;
    private FlightPathNodeRepository flightPathNodeRepository;

    public AStar(List<FlightNodeEntity> graph) {
        this.graph = graph;
    }

    public List<FlightNodeEntity> findPath(FlightNodeEntity start, FlightNodeEntity end) {

        // Inicialização
        gScore = new HashMap<>();
        fScore = new HashMap<>();
        cameFrom = new HashMap<>();

        for (FlightNodeEntity node : graph) {
            gScore.put(node, Double.POSITIVE_INFINITY);
            fScore.put(node, Double.POSITIVE_INFINITY);
        }

        gScore.put(start, 0.0);
        fScore.put(start, haversineDistance(start, end));

        PriorityQueue<FlightNodeEntity> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            FlightNodeEntity current = openSet.poll();

            if (current.equals(end)) {
                return reconstructPath(end);
            }

            for (Path path : current.getPaths()) {

                Long curretId = path.targetId();
                FlightNodeEntity neighbor = flightPathNodeRepository.findById(curretId);

                double weight = path.getElevation() + path.getDistance();

                double tentativeGScore = gScore.get(current) + weight;

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

        return null; // Caminho não encontrado
    }

    private List<FlightNodeEntity> reconstructPath(FlightNodeEntity current) {
        List<FlightNodeEntity> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }

        return path;
    }

    // public String pathToJson(List<FlightNodeEntity> path) {
    //     ObjectMapper mapper = new ObjectMapper();
    //     ArrayNode pathJson = JsonNodeFactory.instance.arrayNode();
    
    //     for (Node node : path) {
    //         ObjectNode nodeJson = JsonNodeFactory.instance.objectNode();
    //         nodeJson.put("latitude", node.getLatitude());
    //         nodeJson.put("longitude", node.getLongitude());
    //         nodeJson.put("elevation", node.getElevation());
    //         pathJson.add(nodeJson);
    //     }
    
    //     return mapper.writeValueAsString(pathJson);
    // }

    public static double haversineDistance(FlightNodeEntity node1, FlightNodeEntity node2) {
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

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000; // multiplicando por 1000 para converter para metros
        double elevationDistance = Math.abs(dAlt);

        return Math.sqrt(distance * distance +elevationDistance *elevationDistance);
    }

    public static void main(String[] args) {
        AStar teste = new AStar(FlightPathNodeRepository.findAll());
        teste.findPath(null, null);
    }
}