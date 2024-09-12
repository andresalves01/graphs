import java.util.*;
import java.util.stream.Collectors;

public final class SimpleGraph implements Graph {
  private final Set<Integer> nodes;
  private final Set<Edge> edges;

  public SimpleGraph(int numberOfNodes, int numberOfEdges) {
    this.nodes = new HashSet<>(numberOfNodes);
    for (int i = 0; i < numberOfEdges; i++) {
      nodes.add(i);
    }

    this.edges = new HashSet<>(numberOfEdges);
  }

  public SimpleGraph(Set<Integer> nodes, Set<Edge> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  @Override
  public final boolean insertNode(int node) {
    return this.nodes.add(node);
  }

  @Override
  public final Set<Integer> getNodes() {
    return this.nodes;
  }

  @Override
  public final boolean contains(int node) {
    return this.nodes.contains(node);
  }

  @Override
  public final boolean removeNode(int node) {
    boolean hasNode = this.nodes.remove(node);
    if (!hasNode) {
      return false;
    }

    return removeEdges(1);
  }

  @Override
  public boolean removeEdges(int node) {
    boolean removedEdges = true;
    for (Integer adjacent : getAdjacentNodes(node)) {
      removedEdges = removedEdges && removeEdge(node, adjacent);
    }

    return removedEdges;

  }

  @Override
  public final boolean insertEdge(int origin, int destination) {
    return this.edges.add(new Edge(origin, destination));
  }

  @Override
  public final Set<Edge> getEdges() {
    return this.edges;
  }

  @Override
  public final boolean removeEdge(int edgeOne, int edgeTwo) {
    return this.edges.remove(new Edge(edgeOne, edgeTwo)) || this.edges.remove(new Edge(edgeTwo, edgeOne));
  }

  @Override
  public final List<Integer> getAdjacentNodes(int node) {
    return this.edges.stream().filter((edge) -> edge.origin == node || edge.destination == node)
        .map((edge) -> edge.origin != node ? edge.origin : edge.destination)
        .collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return Graph.toString(this);
  }
}