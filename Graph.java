import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Graph {
  private ArrayList<Node> nodes;
  public ArrayList<Edge> edges;

  public static Graph fromScanner(Scanner reader) throws IOException {
    Graph graph = new Graph();
    int numberOfNodes = reader.nextInt();

    graph.nodes = new ArrayList<>(numberOfNodes + 1);
    graph.nodes.add(null); // Empty header for 1-based indexing.
    graph.nodes.addAll(Node.createNodes(numberOfNodes));

    graph.edges = Edge.fromScanner(graph, reader);
    return graph;
  }

  public List<Node> getNodes() {
    return this.nodes.stream().filter((node) -> node != null).collect(Collectors.toList());
  }

  public Node getNode(int name) {
    return this.nodes.get(name);
  }

  @Override
  public String toString() {
    String nodes = this.getNodes().stream().map(Node::toString).collect(Collectors.joining(", "));
    String edges = this.edges.stream().map(Edge::toString).collect(Collectors.joining(", "));

    return String.format("G = (%s, %s)", nodes, edges);
  }

}
