import java.util.*;

public class ListGraph{

    private final Map<Node, List<Edge>> nodes = new HashMap<>();

    public ListGraph() {

    }

    public void add(Node node) {
        nodes.put(node, new ArrayList<>());
    }

    public void connect(Node from, Node to, String name, int weight) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            throw new NoSuchElementException("Node " + from + " and/or Node " + to + " not found");
        }
        Edge edgeTo = new Edge(to, name, weight);
        Edge edgeFrom = new Edge(from, name, weight);
        nodes.get(from).add(edgeTo);
        nodes.get(to).add(edgeFrom);
    }
    public void remove (Node from, Node to, String name, int weight){

    }
    public void disconnect(Node from, Node to, String name, int weight) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            throw new NoSuchElementException("Node " + from + " and/or Node " + to + " not found");
        }
        nodes.get(from).removeIf(edge -> edge.getDestination().equals(to));
        nodes.get(to).removeIf(edge -> edge.getDestination().equals(from));
    }
    public void setConnectionWeight(Node from, Node to, int weight) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            throw new NoSuchElementException("Node " + from + " and/or Node " + to + " not found");
        }
        //Exception if edge not excist
        Edge edge = getEdgeBetween(from, to);
        if (edge == null) {
            throw new NoSuchElementException("Edge not found");
        }
        edge.setWeight(weight);
    }

    public Set<Node> getNodes(){
        Set<Node> copyNodes = new HashSet<>();
        copyNodes.addAll(nodes.keySet());

        return copyNodes;
    }

    public List<Edge> getEdgesFrom(Node from){
        if (!nodes.containsKey(from)) {
            throw new NoSuchElementException("Node " + from + " not found");
        }
        List<Edge> copyEdges = new ArrayList<>();
        copyEdges.addAll(nodes.get(from));
        return copyEdges;
    }

    public Edge getEdgeBetween(Node from, Node to) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            throw new NoSuchElementException("Node " + from + " and/or Node " + to + " not found");
        }
        for (Edge edge : nodes.get(from)) {
            if (edge.getDestination().equals(to)) {
                return edge;
            }
        }
        return null;
    }

    public String toString(){
        String ret = "";
        for (Node node : nodes.keySet()) {
            ret += node.toString() + "\n";
            for (Edge edge : nodes.get(node)) {
                ret += edge.toString() + "\n";
            }
            ret += "\n";
        }
        return ret;
    }

    public boolean pathExists(Node from, Node to) {
        Set<Node> visited = new HashSet<>();
        Stack<Node> path = new Stack<>();
        path.push(from);

        return !depthFirstSearch(from, to, visited, path).empty();
    }
    public List<Edge> getPath(Node from, Node to) {
        Set<Node> visited = new HashSet<>();
        Stack<Node> path = new Stack<>();
        path.push(from);

        depthFirstSearch(from, to, visited, path);

        if (path.size() < 2) {
            return null;
        }

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            edges.add(getEdgeBetween(path.get(i), path.get(i + 1)));
        }
        return edges;
    }

    private Stack<Node> depthFirstSearch(Node current, Node searchedFor, Set<Node> visited, Stack<Node> pathSoFar) {
        visited.add(current);
        System.out.println("Visiting " + current.getName());
        if (current.equals(searchedFor)) {
            return pathSoFar;
        }
        for (Edge edge : nodes.get(current)) {
            Node n = edge.getDestination();
            if(!visited.contains(n)) {
                pathSoFar.push(n);
                Stack<Node> p = depthFirstSearch(n, searchedFor, visited, pathSoFar);
                if (!p.isEmpty()) {
                    return p;
                } else {
                    pathSoFar.pop();
                }
            }
        }
        System.out.println("Dead end in " + current.getName());
        return new Stack<Node>();
    }
}