import java.util.ArrayList;
import java.util.stream.Collectors;

public class SuccessorAdjacencyList {
  private final ArrayList<Node> nodes;
  private final ArrayList<ArrayList<Node>> list;

  private SuccessorAdjacencyList(final ArrayList<Node> nodes, final ArrayList<ArrayList<Node>> list) {
    this.nodes = nodes;
    this.list = list;
  }

  public static SuccessorAdjacencyList fromGraph(final Graph graph) {
    ArrayList<Node> nodes = graph.getNodes();
    ArrayList<ArrayList<Node>> list = new ArrayList<>(nodes.size());

    for (Node node : nodes) {
      final ArrayList<Edge> edges = graph.getEdges();
      ArrayList<Node> destinationNodes = new ArrayList<>(edges.stream().filter(edge -> edge.origin == node)
          .map(edge -> edge.destination).collect(Collectors.toList()));

      list.add(destinationNodes);
    }

    return new SuccessorAdjacencyList(nodes, list);
  }

  public final ArrayList<Node> getNodes() {
    return this.nodes;
  }

  public final ArrayList<ArrayList<Node>> getList() {
    return this.list;
  }

  public final int getExitDegree(final int name) {
    return this.getSuccessorNodes(name).size();
  }

  public final ArrayList<Node> getSuccessorNodes(final int name) {
    return this.list.get(name - 1);
  }
}
