import java.util.*;

public interface Graph {

  void add(Node node);

  void connect(Node from, Node to, String name, double distance);

  boolean isConnected(City a, City b);

  Collection<Edge> getPath(City from, City to);

  Collection<Edge> getShortestPath(City from, City to);
}
