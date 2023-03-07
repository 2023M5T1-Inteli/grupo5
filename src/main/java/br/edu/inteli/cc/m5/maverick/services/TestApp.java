package br.edu.inteli.cc.m5.maverick.services;

import java.util.*;

public class TestApp {
    public static void main(String[] args) {
        Graph graph = new Graph();
        // Adiciona as cidades ao grafo
        graph.addCity("Aveiro");
        graph.addCity("Coimbra");
        graph.addCity("Leiria");
        graph.addCity("Lisboa");
        graph.addCity("Porto");
        graph.addCity("Santarem");
        // Adiciona as arestas ao grafo
        graph.addEdge("Aveiro", "Coimbra", 85, 69, 148.5);
        graph.addEdge("Aveiro", "Porto", 76, 2, 120.6);
        graph.addEdge("Coimbra", "Leiria", 65, 124, 104.3);
        graph.addEdge("Coimbra", "Santarem", 164, 176, 203.6);
        graph.addEdge("Leiria", "Lisboa", 131, 74, 132.4);
        graph.addEdge("Lisboa", "Santarem", 78, 6, 58.8);
        graph.addEdge("Porto", "Santarem", 316, 245, 356.3);
        // Encontra o menor caminho entre duas cidades usando o algoritmo A*
        List<String> path = graph.findShortestPath("Aveiro", "Lisboa");
        System.out.println(path);
    }
}
