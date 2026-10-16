package experiment;

import connectivity.TarjanSCC;
import connectivity.TopologicalSort;
import graph.Graph;
import mst.KruskalMST;
import mst.PrimMST;
import shortestpath.DijkstraShortestPath;

import java.util.Random;

public class ExperimentRunner {

    public static void main(String[] args) {
        System.out.println("=== Smart City Infrastructure: Algorithm Benchmarks ===");
        runExperimentsForConfig("Small", 10, 30);
        runExperimentsForConfig("Medium", 100, 500);
        runExperimentsForConfig("Large", 1000, 5000);
    }

    private static void runExperimentsForConfig(String configName, int vertices, int edges) {
        System.out.println("\n--------------------------------------------------");
        System.out.printf("Configuration: %s (V = %d, E = %d)\n", configName, vertices, edges);
        System.out.println("--------------------------------------------------");

        long totalKruskalTime = 0, kruskalOps = 0;
        long totalPrimTime = 0, primOps = 0;
        long totalTarjanTime = 0, tarjanOps = 0;
        long totalTopoTime = 0, topoOps = 0;
        long totalDijkstraTime = 0, dijkstraOps = 0;

        int runs = 5;
        Random seedRandom = new Random(42);

        for (int r = 1; r <= runs; r++) {
            // Generate Undirected Graph for MST & Dijkstra
            Graph undirectedGraph = generateGraph(vertices, edges, false, seedRandom.nextLong());
            // Generate Directed Graph for SCC & Topological Sort
            Graph directedGraph = generateGraph(vertices, edges, true, seedRandom.nextLong());

            // 1. Kruskal
            long start = System.nanoTime();
            KruskalMST kruskal = new KruskalMST();
            kruskal.computeMST(undirectedGraph);
            totalKruskalTime += (System.nanoTime() - start);
            kruskalOps = kruskal.getOperationCount();

            // 2. Prim
            start = System.nanoTime();
            PrimMST prim = new PrimMST();
            prim.computeMST(undirectedGraph);
            totalPrimTime += (System.nanoTime() - start);
            primOps = prim.getOperationCount();

            // 3. Tarjan SCC
            start = System.nanoTime();
            TarjanSCC tarjan = new TarjanSCC();
            tarjan.compute(directedGraph);
            totalTarjanTime += (System.nanoTime() - start);
            tarjanOps = tarjan.getOperationCount();

            // 4. Topological Sort
            start = System.nanoTime();
            TopologicalSort topo = new TopologicalSort();
            topo.compute(directedGraph);
            totalTopoTime += (System.nanoTime() - start);
            topoOps = topo.getOperationCount();

            // 5. Dijkstra
            start = System.nanoTime();
            DijkstraShortestPath dijkstra = new DijkstraShortestPath();
            dijkstra.compute(undirectedGraph, undirectedGraph.getVertex(0));
            totalDijkstraTime += (System.nanoTime() - start);
            dijkstraOps = dijkstra.getOperationCount();
        }

        System.out.printf("Kruskal's Algorithm   : Avg Time = %.3f ms | Ops = %d\n", (totalKruskalTime / 1e6) / runs, kruskalOps);
        System.out.printf("Prim's Algorithm      : Avg Time = %.3f ms | Ops = %d\n", (totalPrimTime / 1e6) / runs, primOps);
        System.out.printf("Tarjan's SCC          : Avg Time = %.3f ms | Ops = %d\n", (totalTarjanTime / 1e6) / runs, tarjanOps);
        System.out.printf("Topological Sort      : Avg Time = %.3f ms | Ops = %d\n", (totalTopoTime / 1e6) / runs, topoOps);
        System.out.printf("Dijkstra's Shortest   : Avg Time = %.3f ms | Ops = %d\n", (totalDijkstraTime / 1e6) / runs, dijkstraOps);
    }

    private static Graph generateGraph(int vertices, int edges, boolean directed, long seed) {
        Random rand = new Random(seed);
        Graph graph = new Graph(directed);

        for (int i = 0; i < vertices; i++) {
            graph.addVertex(i);
        }

        // Ensure connectedness by creating a spanning path first
        for (int i = 0; i < vertices - 1; i++) {
            double weight = 1 + rand.nextDouble() * 99;
            graph.addEdge(i, i + 1, weight);
        }

        // Add remaining random edges
        int currentEdges = vertices - 1;
        while (currentEdges < edges) {
            int src = rand.nextInt(vertices);
            int dest = rand.nextInt(vertices);
            if (src != dest) {
                double weight = 1 + rand.nextDouble() * 99;
                graph.addEdge(src, dest, weight);
                currentEdges++;
            }
        }
        return graph;
    }
}