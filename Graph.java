import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.function.BiFunction;

public interface Graph {

  public boolean insertNode(int node);

  public Set<Integer> getNodes();

  public boolean contains(int node);

  public boolean removeNode(int node);

  public boolean insertEdge(int origin, int destination);

  public Set<Edge> getEdges();

  public List<Integer> getAdjacentNodes(int destination) throws NoSuchElementException;

  public boolean removeEdge(int origin, int destination);

  public boolean removeEdges(int node);

  public static void saveGraph(String filename, Graph graph) throws FileNotFoundException {
    try (PrintWriter writer = new PrintWriter(filename)) {
      Set<Edge> edges = graph.getEdges();
      writer.println(graph.getNodes().size() + " " + edges.size());

      for (Edge edge : edges) {
        writer.println(edge.origin + " " + edge.destination);
      }
    }
  }

  public static Graph fromFile(String fileName, BiFunction<Integer, Integer, Graph> constructor)
      throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(fileName))) {
      int nodesSize = scanner.nextInt();
      int edgesSize = scanner.nextInt();
      Graph graph = constructor.apply(nodesSize, edgesSize);

      for (int i = 0; i < edgesSize; i++) {
        int origin = scanner.nextInt();
        int destination = scanner.nextInt();

        graph.insertEdge(origin, destination);
      }

      return graph;
    }
  }

  public static Graph fromRandomEdges(int numberOfNodes, int numberOfEdges,
      BiFunction<Integer, Integer, Graph> constructor) throws Exception {
    Graph.validateEdgeNumber(numberOfNodes, numberOfEdges);
    Graph graph = constructor.apply(numberOfNodes, numberOfEdges);

    int maxNumberOfEdges = (int) Math.ceil(((numberOfNodes * numberOfEdges) - numberOfEdges) * 0.5);
    if (numberOfEdges == maxNumberOfEdges) {
      for (int i = 0; i < numberOfNodes; i++) {
        for (int j = i + 1; j < numberOfNodes; j++) {
          graph.insertEdge(i, j);
        }
      }

      return graph;
    }

    while (graph.getEdges().size() < numberOfEdges) {
      int origin = (int) (Math.random() * numberOfNodes);
      int destination = (int) (Math.random() * numberOfNodes);
      if (origin == destination) {
        continue;
      }

      boolean originLessThanDestination = origin < destination;
      graph.insertEdge(originLessThanDestination ? origin : destination,
          originLessThanDestination ? destination : origin);
    }

    return graph;
  }

  public static String toString(Graph graph) {
    return "V = {" + graph.getNodes().stream().map((node) -> node.toString()).collect(Collectors.joining(", ")) + "}\n"
        +
        "E = {"
        + graph.getEdges().stream().map((edge) -> "{" + edge.origin + ", " + edge.destination + "}")
            .collect(Collectors.joining(", "))
        + "}";
  }

  public static void validateEdgeNumber(int numberOfNodes, int numberOfEdges) throws Exception {
    int maxNumberOfEdges = (int) Math.ceil(((numberOfNodes * numberOfNodes) - numberOfNodes) * 0.5);
    if (numberOfEdges > maxNumberOfEdges) {
      throw new Exception("O número de arestas ultrapassa o número máximo permitido.");
    }
  }
}
