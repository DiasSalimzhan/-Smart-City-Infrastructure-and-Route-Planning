package graph;

import java.util.Objects;

public class Vertex implements Comparable<Vertex> {
    private final int id;
    private final String name;

    public Vertex(int id) {
        this.id = id;
        this.name = "District_" + id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return name;
    }
}