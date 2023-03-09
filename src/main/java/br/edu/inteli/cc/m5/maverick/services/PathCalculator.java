package br.edu.inteli.cc.m5.maverick.services;

import java.util.*;

// Definition of a new class called PathCalculator
class PathCalculator {
    // Declaration of a String variable called name
    String name;
    // Declaration of a List of Edge objects called edges
    List<Edge> edges;
    // Definition of a constructor for the PathCalculator class that takes a String parameter called name
    public PathCalculator(String name) {
    // Assigning the value of the name parameter to the name variable of the PathCalculator object being created
    this.name = name;
    // Instantiating the edges ArrayList for the PathCalculator object being created
    this.edges = new ArrayList<>();
    }
    // Definition of a method called addEdge that takes an Edge object parameter called e
    public void addEdge(Edge e) {
    // Adding the Edge object passed as parameter to the edges ArrayList of the PathCalculator object
    this.edges.add(e);
    }
    }
    class Edge {
        Region destination; // destination of the edge
        int distance; // distance between the source and destination regions
        int altitudeVariation; // difference in altitude between the source and destination regions
        double hValue; // heuristic value of the edge
        
        // constructor that initializes the attributes of the Edge class
        public Edge(Region destination, int distance, int altitudeVariation, double hValue) { 
            this.destination = destination;
            this.distance = distance;
            this.altitudeVariation = altitudeVariation;
            this.hValue = hValue;
        }
    }
    
    class Region {
        String name; // attribute representing the name of the region
        List<Edge> edges; // attribute that stores the edges connecting the region to other regions
        
        public Region(String name) { // constructor that receives the name of the region
            this.name = name; // assigns the received name to the name attribute
            this.edges = new ArrayList<>(); // initializes the list of edges as an empty list
        }
        
        public void addEdge(Edge e) { // method that adds an edge to the list of edges of the region
            this.edges.add(e); // adds the received edge to the edges attribute
        }
    }
    

    class Graph {
        Map<String, Region> regions; // attribute that stores a map of region names to Region objects
    
        public Graph() { // constructor that initializes an empty map of regions
            this.regions = new HashMap<>();
        }
        
        public void addCity(String name) { // method that adds a new city to the graph
            Region city = new Region(name); // creates a new Region object with the given name
            this.regions.put(name, city); // adds the new region to the map of regions, using its name as the key
        }
    
        public void addEdge(String from, String to, int distance, int altitudeVariation, double hValue) {
            Region city1 = this.regions.get(from); // get the Region object representing the city of departure from the regions map
            Region city2 = this.regions.get(to); // get the Region object representing the destination city from the regions map
            Edge e1 = new Edge(city2, distance, altitudeVariation, hValue); // create a new Edge object to represent the connection from city1 to city2
            Edge e2 = new Edge(city1, distance, altitudeVariation, hValue); // create a new Edge object to represent the connection from city2 to city1
            city1.addEdge(e1); // add e1 to the list of edges for city1
            city2.addEdge(e2); // add e2 to the list of edges for city2
        }
        
        public List<String> findShortestPath(String start, String goal) {
            // create a new HashMap to store the gScore for each region
            Map<Region, Double> gScore = new HashMap<>();
            // set the gScore for the starting region to 0
            gScore.put(regions.get(start), 0.0);
            
            // create a new HashMap to store the fScore for each region
            Map<Region, Double> fScore = new HashMap<>();
            // set the fScore for the starting region to 0
            fScore.put(regions.get(start), 0.0);
            
            // create a new HashMap to store the previous region in the optimal path
            Map<Region, Region> cameFrom = new HashMap<>();
            
            // create a new HashSet to store the visited regions
            Set<Region> visited = new HashSet<>();
            
            // create a new PriorityQueue to store the regions to be explored
            PriorityQueue<Region> frontier = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
            // add the starting region to the frontier
            frontier.add(regions.get(start));
        
            while (!frontier.isEmpty()) { // loop while the priority queue frontier is not empty
                Region current = frontier.poll(); // get the next region with the lowest fScore from the priority queue
                visited.add(current); // mark the current region as visited
                if (current.name.equals(goal)) { // if the current region is the goal region
                    return reconstructPath(cameFrom, current); // reconstruct and return the shortest path from start to goal using the cameFrom map
                }
                for (Edge e : current.edges) { // for each edge in the current region's list of edges
                    Region neighbor = e.destination; // get the neighbor region that the current edge connects to
                    double tentativeGScore = gScore.get(current) + e.distance + e.altitudeVariation; // calculate the tentative gScore for the neighbor
                    if (visited.contains(neighbor) && tentativeGScore >= gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                        // if the neighbor has been visited and the tentative gScore is not better than the neighbor's current gScore
                        continue; // skip to the next edge
                    }
                    cameFrom.put(neighbor, current); // update the cameFrom map with the current region as the parent of the neighbor region
                    gScore.put(neighbor, tentativeGScore); // update the gScore map with the tentative gScore for the neighbor
                    fScore.put(neighbor, tentativeGScore + e.hValue); // update the fScore map with the sum of the tentative gScore and the heuristic hValue for the neighbor
                    if (!frontier.contains(neighbor)) { // if the priority queue does not already contain the neighbor
                        frontier.add(neighbor); // add the neighbor to the priority queue
                    }
                }
            }
            return null; // if the loop ends without finding a path, return null
            }
            
            private List<String> reconstructPath(Map<Region, Region> cameFrom, Region current) {
                List<String> path = new ArrayList<>(); // create a new list to store the path
                path.add(current.name); // add the name of the goal region to the end of the path list
                while (cameFrom.containsKey(current)) { // loop while the current region has a parent in the cameFrom map
                    current = cameFrom.get(current); // set the current region to its parent
                    path.add(0, current.name); // add the name of the parent region to the beginning of the path list
                }
                return path; // return the reconstructed path
            }
        }
            