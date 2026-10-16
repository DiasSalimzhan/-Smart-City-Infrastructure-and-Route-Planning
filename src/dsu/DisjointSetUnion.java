package dsu;

import java.util.HashMap;
import java.util.Map;

public class DisjointSetUnion<T> {
    private final Map<T, T> parent = new HashMap<>();
    private final Map<T, Integer> rank = new HashMap<>();
    private long operationCount = 0;

    public void makeSet(T element) {
        parent.put(element, element);
        rank.put(element, 0);
        operationCount++;
    }

    public T find(T element) {
        operationCount++;
        if (!parent.containsKey(element)) {
            makeSet(element);
        }
        T root = parent.get(element);
        if (!root.equals(element)) {
            root = find(root); // Path compression
            parent.put(element, root);
        }
        return root;
    }

    public boolean union(T element1, T element2) {
        operationCount++;
        T root1 = find(element1);
        T root2 = find(element2);

        if (root1.equals(root2)) {
            return false; // Already in the same set (cycle detected)
        }

        // Union by rank
        int rank1 = rank.get(root1);
        int rank2 = rank.get(root2);

        if (rank1 < rank2) {
            parent.put(root1, root2);
        } else if (rank1 > rank2) {
            parent.put(root2, root1);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank1 + 1);
        }
        operationCount++;
        return true;
    }

    public long getOperationCount() {
        return operationCount;
    }

    public void resetOperationCount() {
        this.operationCount = 0;
    }
}