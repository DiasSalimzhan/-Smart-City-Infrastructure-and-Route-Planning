package connectivity;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.*;

public class TarjanSCC {
    private final List<List<Vertex>> sccs = new ArrayList<>();
    private Map<Vertex, Integer> disc;
    private Map<Vertex, Integer> low;
    private Deque<Vertex> stack;
    private Set<Vertex> inStack;
    private int time;
    private long operationCount;

    public void compute(Graph graph) {
        sccs.clear();
        disc = new HashMap<>();
        low = new HashMap<>();
        stack = new ArrayDeque<>();
        inStack = new HashSet<>();
        time = 0;
        operationCount = 0;

        for (Vertex v : graph.getVertices()) {
            if (!disc.containsKey(v)) {
                dfs(v, graph);
            }
        }
    }

    private void dfs(Vertex u, Graph graph) {
        operationCount++;
        disc.put(u, time);
        low.put(u, time);
        time++;
        stack.push(u);
        inStack.add(u);

        for (Edge edge : graph.getAdjacencyList(u)) {
            operationCount++;
            Vertex v = edge.getDestination();

            if (!disc.containsKey(v)) {
                dfs(v, graph);
                low.put(u, Math.min(low.get(u), low.get(v)));
            } else if (inStack.contains(v)) {
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }

        if (low.get(u).equals(disc.get(u))) {
            List<Vertex> scc = new ArrayList<>();
            Vertex v;
            do {
                v = stack.pop();
                inStack.remove(v);
                scc.add(v);
            } while (!v.equals(u));
            sccs.add(scc);
        }
    }

    public List<List<Vertex>> getSccs() {
        return sccs;
    }

    public long getOperationCount() {
        return operationCount;
    }
}