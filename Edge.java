import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Edge {
  public Node origin;
  public Node destination;

  public Edge(Node origin, Node destination) {
    this.origin = origin;
    this.destination = destination;
  }

  public static ArrayList<Edge> fromScanner(Graph graph, Scanner reader) throws IOException {
    int numberOfEdges = reader.nextInt();

    ArrayList<Edge> edges = new ArrayList<>(numberOfEdges);
    for (int i = 0; i < numberOfEdges; ++i) {
      int origin = reader.nextInt();
      int destination = reader.nextInt();
      edges.add(new Edge(graph.getNode(origin), graph.getNode(destination)));
    }

    return edges;
  }

  @Override
  public String toString() {
    return "(" + this.origin.toString() + ", " + this.destination.toString() + ")";
  }
}
