import java.util.*;
import java.util.stream.Collectors;

public final class Graph extends IGraph {
  private final Set<Edge> edges;

  public Graph(int numberOfNodes, int numberOfEdges) {
    super(numberOfNodes);
    this.edges = new HashSet<>(numberOfEdges);
  }

  public Graph(Set<Integer> nodes, Set<Edge> edges) {
    super(nodes);
    this.edges = edges;
  }

  public boolean insertEdge(int origin, int destination) {
    return this.edges.add(new Edge(origin, destination));
  }

  public List<Edge> getEdges() {
    return edges.stream().collect(Collectors.toList());
  }

  public boolean removeEdge(int edgeOne, int edgeTwo) {
    return this.edges.remove(new Edge(edgeOne, edgeTwo)) || this.edges.remove(new Edge(edgeTwo, edgeOne));
  }

  public List<Integer> getAdjacents(int node) {
    return this.edges.stream().filter((edge) -> edge.origin == node || edge.destination == node)
        .map((edge) -> edge.origin != node ? edge.origin : edge.destination)
        .collect(Collectors.toList());
  }
}