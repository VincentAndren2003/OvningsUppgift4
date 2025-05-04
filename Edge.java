import java.util.Objects;

public class Edge {
    private final String name;
    private int weight;
    private final Node destination;

    public Edge(Node destination, String name, int weight) {
        this.name = name;
        this.weight = weight;
        this.destination = destination;
    }

    public Node getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (weight < 0){
            throw new IllegalArgumentException("Weight must be a positive number");
        }
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return name + " " + weight + " " + destination;
    }
}