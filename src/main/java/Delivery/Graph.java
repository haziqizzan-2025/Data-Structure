package Delivery;

import java.util.*;

public class Graph {
    // Adjacency list representation: Map<Source, Map<Destination, Weight>>
    private final Map<String, Map<String, Integer>> adjacencyList = new HashMap<>();

    // Add a biological or street connection between two location nodes (undirected network)
    public void addEdge(String source, String destination, int distance) {
        adjacencyList.putIfAbsent(source, new HashMap<>());
        adjacencyList.putIfAbsent(destination, new HashMap<>());
        
        adjacencyList.get(source).put(destination, distance);
        adjacencyList.get(destination).put(source, distance); // Assuming two-way roads
    }

    // Dijkstra's Algorithm implementation
    public void findShortestPath(String startNode, String endNode) {
        if (!adjacencyList.containsKey(startNode) || !adjacencyList.containsKey(endNode)) {
            System.out.println("[Error]: One or both locations do not exist in the routing network.");
            return;
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        Set<String> visited = new HashSet<>();

        // Initialize distances
        for (String location : adjacencyList.keySet()) {
            distances.put(location, Integer.MAX_VALUE);
        }
        distances.put(startNode, 0);
        minHeap.add(new Node(startNode, 0));

        while (!minHeap.isEmpty()) {
            Node current = minHeap.poll();
            String u = current.name;

            if (visited.contains(u)) continue;
            visited.add(u);

            // Target short-circuit
            if (u.equals(endNode)) break;

            // Relax edges
            Map<String, Integer> neighbors = adjacencyList.get(u);
            if (neighbors != null) {
                for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                    String v = neighbor.getKey();
                    int weight = neighbor.getValue();

                    if (!visited.contains(v)) {
                        int newDist = distances.get(u) + weight;
                        if (newDist < distances.get(v)) {
                            distances.put(v, newDist);
                            predecessors.put(v, u);
                            minHeap.add(new Node(v, newDist));
                        }
                    }
                }
            }
        }

        // Output formatting
        if (distances.get(endNode) == Integer.MAX_VALUE) {
            System.out.println("No route found between " + startNode + " and " + endNode);
        } else {
            System.out.println("\n===== OPTIMIZED ROUTE FOUND =====");
            System.out.println("Total Cost/Distance: " + distances.get(endNode) + " mins/km");
            System.out.print("Path: ");
            
            // Reconstruct the path backwards
            List<String> path = new ArrayList<>();
            String step = endNode;
            while (step != null) {
                path.add(0, step);
                step = predecessors.get(step);
            }
            
            System.out.println(String.join(" -> ", path));
            System.out.println("=================================");
        }
    }

    // Helper Node structure for the Min-Heap Priority Queue
    private static class Node {
        String name;
        int distance;

        Node(String name, int distance) {
            this.name = name;
            this.distance = distance;
        }
    }
}
