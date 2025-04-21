import java.util.*;

public interface Graph {

  void add(City city);

  void connect(City from, City to, String name, double distance);

  boolean isConnected(Node a, Node b);

  Collection<Edge> getPath(Node from, Node to);

  Collection<Edge> getShortestPath(Node from, Node to);
}
