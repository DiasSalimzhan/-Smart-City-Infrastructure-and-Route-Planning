package shortestpath;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.*;

public class DijkstraShortestPath {
    private final Map<Vertex, Double> distances = new HashMap<>();
    private final Map<Vertex, Vertex> predecessor = new HashMap<>();
    private long operationCount = 0;

    public void compute(Graph graph, Vertex source) {
        distances.clear();
        predecessor.clear();
        operationCount = 0;

        for (Vertex v : graph.getVertices()) {
            distances.put(v, Double.MAX_VALUE);
        }
        distances.put(source, 0.0);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));
        pq.add(new Node(source, 0.0));

        while (!pq.isEmpty()) {
            operationCount++;
            Node current = pq.poll();
            Vertex u = current.vertex;

            if (current.distance > distances.get(u)) {
                continue;
            }

            for (Edge edge : graph.getAdjacencyList(u)) {
                operationCount++;
                Vertex v = edge.getDestination();
                double weight = edge.getWeight();

                if (distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                    predecessor.put(v, u);
                    pq.add(new Node(v, distances.get(v)));
                }
            }
        }
    }

    public List<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex curr = target;
        if (!distances.containsKey(curr) || distances.get(curr) == Double.MAX_VALUE) {
            return path; // Empty if unreachable
        }
        while (curr != null) {
            path.addFirst(curr);
            curr = predecessor.get(curr);
        }
        return path;
    }

    public double getDistance(Vertex target) {
        return distances.getOrDefault(target, Double.MAX_VALUE);
    }

    public long getOperationCount() {
        return operationCount;
    }

    private static class Node {
        Vertex vertex;
        double distance;

        Node(Vertex vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        double getDistance() {
            return distance;
        }
    }
}