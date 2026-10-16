package mst;

import dsu.DisjointSetUnion;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {
    private List<Edge> mstEdges;
    private double totalWeight;
    private long operationCount;

    public void computeMST(Graph graph) {
        mstEdges = new ArrayList<>();
        totalWeight = 0.0;
        operationCount = 0;

        DisjointSetUnion<Vertex> dsu = new DisjointSetUnion<>();
        for (Vertex v : graph.getVertices()) {
            dsu.makeSet(v);
        }

        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges);

        for (Edge edge : sortedEdges) {
            operationCount++; // Edge examination
            Vertex u = edge.getSource();
            Vertex v = edge.getDestination();

            if (dsu.union(u, v)) {
                mstEdges.add(edge);
                totalWeight += edge.getWeight();
                if (mstEdges.size() == graph.vertexCount() - 1) {
                    break;
                }
            }
        }
        operationCount += dsu.getOperationCount();
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