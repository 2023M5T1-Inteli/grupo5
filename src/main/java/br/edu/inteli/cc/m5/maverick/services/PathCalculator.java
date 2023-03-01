package br.edu.inteli.cc.m5.maverick.services;

import java.util.*;
class PathCalculator {
    String name;
    List<Edge> edges;
    public City(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }
    public void addEdge(Edge e) {
        this.edges.add(e);
    }
}
class Edge {
    City destination;
    int distance;
    int altitudeVariation;
    double hValue; // valor heurístico
    public Edge(City destination, int distance, int altitudeVariation, double hValue) {
        this.destination = destination;
        this.distance = distance;
        this.altitudeVariation = altitudeVariation;
        this.hValue = hValue;
    }
}
class Graph {
    Map<String, City> cities;
    public Graph() {
        this.cities = new HashMap<>();
    }
    public void addCity(String name) {
        City city = new City(name);
        this.cities.put(name, city);
    }
    public void addEdge(String from, String to, int distance, int altitudeVariation, double hValue) {
        City city1 = this.cities.get(from);
        City city2 = this.cities.get(to);
        Edge e1 = new Edge(city2, distance, altitudeVariation, hValue);
        Edge e2 = new Edge(city1, distance, altitudeVariation, hValue);
        city1.addEdge(e1);
        city2.addEdge(e2);
    }
    public List<String> findShortestPath(String start, String goal) {
        Map<City, Double> gScore = new HashMap<>();
        gScore.put(cities.get(start), 0.0);
        Map<City, Double> fScore = new HashMap<>();
        fScore.put(cities.get(start), 0.0);
        Map<City, City> cameFrom = new HashMap<>();
        Set<City> visited = new HashSet<>();
        PriorityQueue<City> frontier = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        frontier.add(cities.get(start));
        while (!frontier.isEmpty()) {
            City current = frontier.poll();
            visited.add(current);
            if (current.name.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }
            for (Edge e : current.edges) {
                City neighbor = e.destination;
                double tentativeGScore = gScore.get(current) + e.distance + e.altitudeVariation;
                if (visited.contains(neighbor) && tentativeGScore >= gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    continue;
                }
                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentativeGScore);
                fScore.put(neighbor, tentativeGScore + e.hValue);
                if (!frontier.contains(neighbor)) {
                    frontier.add(neighbor);
                }
            }
        }
        return null;
    }
    private List<String> reconstructPath(Map<City, City> cameFrom, City current) {
        List<String> path = new ArrayList<>();
        path.add(current.name);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current.name);
        }
        return path;
    }
}