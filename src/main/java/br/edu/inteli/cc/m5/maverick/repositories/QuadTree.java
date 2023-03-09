package br.edu.inteli.cc.m5.maverick.repositories;

import java.util.*;

public class QuadTree {
    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private final int capacity;
    private final List<Node> nodes;
    private QuadTree nw;
    private QuadTree ne;
    private QuadTree sw;
    private QuadTree se;

    public QuadTree(double x, double y, double width, double height, int capacity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.capacity = capacity;
        this.nodes = new ArrayList<>();
        
    }
    

    public QuadTree getNW() {
        return this.nw;
    }

    public QuadTree getNE() {
        return this.ne;
    }

    public QuadTree getSW() {
        return this.sw;
    }

    public QuadTree getSE() {
        return this.se;
    }

    public void insert(Node node) {
        if (nodes.size() < capacity) {
            nodes.add(node);
            Node.mergeSort(nodes);
        } else {
            if (nw == null) {
                split();
            }
            if (node.getLongitude() < x + width/2 && node.getLatitude() < y + height/2) {
                nw.insert(node);
            } else if (node.getLongitude() >= x + width/2 && node.getLatitude() < y + height/2) {
                ne.insert(node);
            } else if (node.getLongitude() < x + width/2 && node.getLatitude() >= y + height/2) {
                sw.insert(node);
            } else {
                se.insert(node);
            }
        }
    }

    public List<Node> queryRange(double xMin, double yMin, double xMax, double yMax) {
        List<Node> found = new ArrayList<>();
        if (x + width < xMin || x > xMax || y + height < yMin || y > yMax) {
            return found;
        }
        for (Node node : nodes) {
            if (node.getLongitude() >= xMin && node.getLongitude() <= xMax && node.getLatitude() >= yMin && node.getLatitude() <= yMax) {
                found.add(node);
            }
        }
        if (nw != null) {
            found.addAll(nw.queryRange(xMin, yMin, xMax, yMax));
            found.addAll(ne.queryRange(xMin, yMin, xMax, yMax));
            found.addAll(sw.queryRange(xMin, yMin, xMax, yMax));
            found.addAll(se.queryRange(xMin, yMin, xMax, yMax));
        }
        return found;
    }

    private void split(List<Node> parentNodes) {
        double subWidth = width / 2;
        double subHeight = height / 2;
        nw = new QuadTree(x, y, subWidth, subHeight, capacity);
        ne = new QuadTree(x + subWidth, y, subWidth, subHeight, capacity);
        sw = new QuadTree(x, y + subHeight, subWidth, subHeight, capacity);
        se = new QuadTree(x + subWidth, y + subHeight, subWidth, subHeight, capacity);
        for (Node node : parentNodes) {
            if (node.getLongitude() < x + subWidth && node.getLatitude() < y + subHeight) {
                nw.getNodes().add(node);
            } else if (node.getLongitude() >= x + subWidth && node.getLatitude() < y + subHeight) {
                ne.getNodes().add(node);
            } else if (node.getLongitude() < x + subWidth && node.getLatitude() >= y + subHeight) {
                sw.getNodes().add(node);
            } else {
                se.getNodes().add(node);
            }
        }
    }
    
    

    public List<Node> getNodes() {
        List<Node> allNodes = new ArrayList<>();
        allNodes.addAll(nodes);
        if (nw != null) {
            allNodes.addAll(nw.getNodes());
            allNodes.addAll(ne.getNodes());
            allNodes.addAll(sw.getNodes());
            allNodes.addAll(se.getNodes());
        }
        return allNodes;
    }
    
}
