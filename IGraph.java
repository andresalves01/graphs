import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.function.BiFunction;

public abstract class IGraph {
  private final Set<Integer> nodes;

  public IGraph(int nodeSize) {
    this.nodes = new HashSet<Integer>(nodeSize);
    for (int i = 0; i < nodeSize; i++) {
      nodes.add(i);
    }
  }

  public IGraph(Set<Integer> nodes) {
    this.nodes = nodes;
  }

  public final Set<Integer> getNodes() {
    return nodes;
  }

  public final boolean insertNode(int node) {
    if (nodes.contains(node)) {
      return false;
    }

    return nodes.add(node);
  }

  public final boolean contains(int node) {
    return nodes.contains(node);
  }

  public final boolean removeNode(int node) {
    removeEdges(node);
    return nodes.remove(node);
  }

  public abstract boolean insertEdge(int origin, int destination);

  public abstract List<Edge> getEdges();

  public abstract List<Integer> getAdjacents(int destination) throws NoSuchElementException;

  public abstract boolean removeEdge(int edgeOne, int edgeTwo);

  public final void removeEdges(int node) {
    for (int origin : getAdjacents(node)) {
      removeEdge(origin, node);
    }
  }

  public void saveGraph(String filename) throws FileNotFoundException {
    try (PrintWriter writer = new PrintWriter(filename)) {
      List<Edge> edges = getEdges();
      writer.println(nodes.size() + " " + edges.size());

      for (Edge edge : edges) {
        writer.println(edge.origin + " " + edge.destination);
      }
    }
  }

  public static IGraph fromFile(String fileName, BiFunction<Integer, Integer, IGraph> constructor)
      throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(fileName))) {
      int nodesSize = scanner.nextInt();
      int edgesSize = scanner.nextInt();
      IGraph graph = constructor.apply(nodesSize, edgesSize);

      for (int i = 0; i < edgesSize; i++) {
        int origin = scanner.nextInt();
        int destination = scanner.nextInt();

        graph.insertEdge(origin, destination);
      }

      return graph;
    }
  }

  public static IGraph fromRandomEdges(int numberOfNodes, int numberOfEdges,
      BiFunction<Integer, Integer, IGraph> constructor) throws Exception {
    IGraph.validateEdgeNumber(numberOfNodes, numberOfEdges);
    IGraph graph = constructor.apply(numberOfNodes, numberOfEdges);

    int maxNumberOfEdges = (int) Math.ceil(((numberOfNodes * numberOfEdges) - numberOfEdges) * 0.5);
    if (numberOfEdges == maxNumberOfEdges) {
      for (int i = 0; i < numberOfNodes; i++) {
        for (int j = i + 1; j < numberOfNodes; j++) {
          graph.insertEdge(i, j);
        }
      }

      return graph;
    }

    while (graph.getEdges().size() != numberOfEdges) {
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

  @Override
  public String toString() {
    return "V = {" + nodes.stream().map((node) -> node.toString()).collect(Collectors.joining(", ")) + "}\n" +
        "E = {"
        + getEdges().stream().sorted((first, second) -> first.origin - second.origin)
            .map((edge) -> "{" + edge.origin + ", " + edge.destination + "}")
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
