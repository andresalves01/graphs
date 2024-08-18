import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class Graph {
  private final ArrayList<Node> nodes;
  private final ArrayList<Edge> edges;

  public Graph(final ArrayList<Node> nodes, final int numberOfEdges) {
    this.nodes = nodes;
    this.edges = new ArrayList<>(numberOfEdges);
  }

  public static Graph fromScanner(final Scanner reader) throws IOException {
    int numberOfNodes = reader.nextInt();
    ArrayList<Node> nodes = Node.createNodes(numberOfNodes);

    int numberOfEdges = reader.nextInt();
    Graph graph = new Graph(nodes, numberOfEdges);

    for (int i = 0; i < numberOfEdges; ++i) {
      int origin = reader.nextInt();
      int destination = reader.nextInt();
      graph.insertEdge(origin, destination);
    }

    return graph;
  }

  public final ArrayList<Node> getNodes() {
    return this.nodes;
  }

  public final Node getNode(final int name) {
    return this.nodes.get(name - 1);
  }

  public final ArrayList<Edge> getEdges() {
    return this.edges;
  }

  public final void insertEdge(final int originName, final int destinationName) {
    Node origin = this.getNode(originName);
    Node destination = this.getNode(destinationName);

    this.edges.add(new Edge(origin, destination));
  }

  @Override
  public final String toString() {
    String nodes = this.getNodes().stream().map(Node::toString).collect(Collectors.joining(", "));
    String edges = this.edges.stream().map(Edge::toString).collect(Collectors.joining(", "));

    return String.format("G = (%s, %s)", nodes, edges);
  }

}
