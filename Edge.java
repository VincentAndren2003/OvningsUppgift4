import java.util.Objects;

public class Edge {
    private final String name;
    private final double distance;
    private final Node destination;

    public Edge(Node destination, String name, double distance) {
        this.destination = Objects.requireNonNull(destination);
        this.name = Objects.requireNonNull(name);

        if (Double.isNaN(distance)) {
            throw new IllegalArgumentException();
        }
        this.distance = distance;
    }

    public Node getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge edge) {
            return destination.equals(edge.destination) && name.equals(edge.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, name);
    }

    @Override
    public String toString() {
        return "Edge[destination=" + destination + ", name=" + name + " distance=" + distance + "]";
    }
}
