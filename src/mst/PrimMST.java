package mst;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.*;

public class PrimMST {
    private List<Edge> mstEdges;
    private double totalWeight;
    private long operationCount;

    public void computeMST(Graph graph) {
        mstEdges = new ArrayList<>();
        totalWeight = 0.0;
        operationCount = 0;

        if (graph.getVertices().isEmpty()) return;

        Set<Vertex> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        Vertex startVertex = graph.getVertices().iterator().next();
        visited.add(startVertex);
        addEdges(startVertex, graph, visited, pq);

        while (!pq.isEmpty() && visited.size() < graph.vertexCount()) {
            operationCount++; // Priority queue poll operation
            Edge edge = pq.poll();
            Vertex v = edge.getDestination();

            if (visited.contains(v)) {
                continue;
            }

            visited.add(v);
            mstEdges.add(edge);
            totalWeight += edge.getWeight();

            addEdges(v, graph, visited, pq);
        }
    }

    private void addEdges(Vertex vertex, Graph graph, Set<Vertex> visited, PriorityQueue<Edge> pq) {
        for (Edge edge : graph.getAdjacencyList(vertex)) {
            operationCount++; // Edge examination
            if (!visited.contains(edge.getDestination())) {
                pq.add(edge);
            }
        }
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public long getOperationCount() {
        return operationCount;
    }
}