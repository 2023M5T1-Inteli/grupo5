
package br.edu.inteli.cc.m5.maverick.repositories;
import br.edu.inteli.cc.m5.maverick.controllers.FlightPathController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {

    private List<Node> nodes;
    private List<Edge> edgesObject;
    private Map<Node, List<Node>> edges;

    public Graph(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = new HashMap<>();

        // Initialize edges map
        for (Node node : nodes) {
            this.edges.put(node, new ArrayList<>());
        }

        // Add edges to map
        for (Edge edge : edges) {
            this.edges.get(edge.getSource()).add(edge.getDestination());
            this.edges.get(edge.getDestination()).add(edge.getSource());
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Node> getNeighbors(Node node) {
        return edges.get(node);
    }

    public boolean hasEdge(Node source, Node destination) {
        return edges.get(source).contains(destination);

    }

    public Edge getEdge(Node source, Node destination) {
        for (Edge edge : edgesObject) {
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination)) {
                return edge;
            }
        }
        return null;
    }

    // public List<Edge> createGridEdges(List<Node> nodes, double maxRelativeError) {
    //     List<Edge> edges = new ArrayList<>();
    
    //     // Sort nodes by latitude and longitude
    //     nodes = Node.mergeSort(nodes);
    
    //     int numRows = (int) Math.sqrt(nodes.size());
    
    //     for (int i = 0; i < nodes.size(); i++) {
    //         Node node = nodes.get(i);
    //         int row = i / numRows;
    //         int col = i % numRows;
    
    //         // Check for neighboring nodes to connect
    //         if (row > 0) {
    //             Node north = nodes.get(i - numRows);
    //             if (Math.abs(north.getLatitude() - node.getLatitude()) / node.getLatitude() <= maxRelativeError) {
    //                 double distance = haversineDistance(node, north);
    //                 double elevationVariation = Math.abs(node.getElevation() - north.getElevation());
    //                 edges.add(new Edge(node, north, distance, elevationVariation));
    //             }
    //         }
    //         if (row < numRows - 1) {
    //             Node south = nodes.get(i + numRows);
    //             if (Math.abs(south.getLatitude() - node.getLatitude()) / node.getLatitude() <= maxRelativeError) {
    //                 double distance = haversineDistance(node, south);
    //                 double elevationVariation = Math.abs(node.getElevation() - south.getElevation());
    //                 edges.add(new Edge(node, south, distance, elevationVariation));
    //             }
    //         }
    //         if (col > 0) {
    //             Node west = nodes.get(i - 1);
    //             if (Math.abs(west.getLongitude() - node.getLongitude()) / node.getLongitude() <= maxRelativeError) {
    //                 double distance = haversineDistance(node, west);
    //                 double elevationVariation = Math.abs(node.getElevation() - west.getElevation());
    //                 edges.add(new Edge(node, west, distance, elevationVariation));
    //             }
    //         }
    //         if (col < numRows - 1) {
    //             Node east = nodes.get(i + 1);
    //             if (Math.abs(east.getLongitude() - node.getLongitude()) / node.getLongitude() <= maxRelativeError) {
    //                 double distance = haversineDistance(node, east);
    //                 double elevationVariation = Math.abs(node.getElevation() - east.getElevation());
    //                 edges.add(new Edge(node, east, distance, elevationVariation));
    //             }
    //         }
    //     }
    
    //     return edges;
    // }

    public static List<Edge> addEdges(QuadTree quadTree, double max) {
        final double MAX_DISTANCE = max; 
        final double RANGE_MAX = MAX_DISTANCE/ 6371000;
        List<Edge> edges = new ArrayList<>();
        for (Node node : quadTree.getNodes()) {
            List<Node> neighbors = quadTree.queryRange(node.getLongitude() - RANGE_MAX, node.getLatitude() - RANGE_MAX, node.getLongitude() + RANGE_MAX, node.getLatitude() + RANGE_MAX);
            for (Node neighbor : neighbors) {
                if (!node.equals(neighbor)) {
                    double distance = node.distanceTo(neighbor);
                    if (distance < MAX_DISTANCE) {
                        double elevationVariation = Math.abs(node.getElevation() - neighbor.getElevation());
                        edges.add(new Edge(node, neighbor, distance, elevationVariation));
                    }
                }
            }
        }
        if (quadTree.getNW() != null) {
            edges.addAll(addEdges(quadTree.getNW(), max));
            edges.addAll(addEdges(quadTree.getNE(), max));
            edges.addAll(addEdges(quadTree.getSW(), max));
            edges.addAll(addEdges(quadTree.getSE(), max));
        }
        return edges;
    }
    

    public double haversineDistance(Node node1, Node node2) {
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
        // FlightPathController neo4jDB = new FlightPathController(null, null);
        // List<Node> nodes = neo4jDB.getNodes();

        double lat = -15.7942; // latitude do ponto central
        double lon = -47.8825; // longitude do ponto central
        double elev = 1000.0; // elevação inicial
        int gridSize = 1000; // tamanho da grelha (5x5)
        double distance = 1000.0; // distância entre os nós
        


        // // Criar uma lista com 20 nós aleatórios
        // List<Node> nodes = Node.generateNodeList(lat, lon, elev, gridSize, distance);
        
        
        
        // // Criar um quadtree com os nós
        // QuadTree quadtree = new QuadTree(-180, 90, 180, -90, 5);

        // for (Node node : nodes) {
        //     quadtree.insert(node);
        // }        

        // // KdTree kdTree = new KdTree(nodes);
        // List<Edge> edges = new ArrayList<>();
        // edges.addAll(addEdges(quadtree, 150));
        // Graph graph = new Graph(nodes, edges);

        // // Node start = new Node(45, 23, 100);
        // // Node end = new Node(23, 34, 500);

        // Node start = nodes.get(0);
        // Node end = nodes.get(7902);
        

        // AStar aStar = new AStar(graph);

        // String jSONPath = aStar.pathToJson(aStar.findPath(start, end));

        // // Imprimir as arestas geradas
        // for (Edge edge : edges) {
        //     System.out.println(edge);
        // }

        // System.out.println(jSONPath);
    }

}