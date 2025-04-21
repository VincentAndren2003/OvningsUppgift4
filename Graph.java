import java.util.*;

public interface Graph {

  void add(Node node);

  void connect(Node from, Node to, String name, double distance);

  boolean isConnected(Node a, Node b);

  Collection<Edge> getPath(Node from, Node to);

  Collection<Edge> getShortestPath(Node from, Node to);
}
