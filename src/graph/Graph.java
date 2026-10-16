package graph;

import java.util.*;

public class Graph {
    private final Map<Integer, Vertex> vertices;
    private final List<Edge> edges;
    private final Map<Vertex, List<Edge>> adjacencyList;
    private final boolean directed;

    public Graph(boolean directed) {
        this.vertices = new TreeMap<>();
        this.edges = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
        this.directed = directed;
    }

    public void addVertex(int id) {
        vertices.putIfAbsent(id, new Vertex(id));
        adjacencyList.putIfAbsent(vertices.get(id), new ArrayList<>());
    }

    public void addEdge(int srcId, int destId, double weight) {
        addVertex(srcId);
        addVertex(destId);

        Vertex src = vertices.get(srcId);
        Vertex dest = vertices.get(destId);

        Edge edge = new Edge(src, dest, weight);
        edges.add(edge);
        adjacencyList.get(src).add(edge);

        if (!directed) {
            Edge reverseEdge = new Edge(dest, src, weight);
            adjacencyList.get(dest).add(reverseEdge);
        }
    }

    public Collection<Vertex> getVertices() {
        return vertices.values();
    }

    public Vertex getVertex(int id) {
        return vertices.get(id);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Edge> getAdjacencyList(Vertex vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptyList());
    }

    public boolean isDirected() {
        return directed;
    }

    public int vertexCount() {
        return vertices.size();
    }

    public int edgeCount() {
        return edges.size();
    }
}