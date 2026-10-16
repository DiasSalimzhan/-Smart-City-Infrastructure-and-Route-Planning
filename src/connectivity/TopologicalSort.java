package connectivity;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.*;

public class TopologicalSort {
    private final List<Vertex> order = new ArrayList<>();
    private boolean hasCycle = false;
    private long operationCount = 0;

    public void compute(Graph graph) {
        order.clear();
        hasCycle = false;
        operationCount = 0;

        Map<Vertex, Integer> inDegree = new HashMap<>();
        for (Vertex v : graph.getVertices()) {
            inDegree.put(v, 0);
        }

        for (Edge e : graph.getEdges()) {
            operationCount++;
            Vertex dest = e.getDestination();
            inDegree.put(dest, inDegree.get(dest) + 1);
        }

        Queue<Vertex> queue = new LinkedList<>();
        for (Map.Entry<Vertex, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            operationCount++;
            Vertex curr = queue.poll();
            order.add(curr);

            for (Edge e : graph.getAdjacencyList(curr)) {
                operationCount++;
                Vertex neighbor = e.getDestination();
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (order.size() != graph.vertexCount()) {
            hasCycle = true;
        }
    }

    public List<Vertex> getOrder() {
        return order;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public long getOperationCount() {
        return operationCount;
    }
}