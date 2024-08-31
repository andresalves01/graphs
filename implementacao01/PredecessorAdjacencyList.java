import java.util.ArrayList;
import java.util.stream.Collectors;

public class PredecessorAdjacencyList {
  private final ArrayList<Node> nodes;
  private final ArrayList<ArrayList<Node>> predecessors;

  private PredecessorAdjacencyList(final ArrayList<Node> nodes, final ArrayList<ArrayList<Node>> list) {
    this.nodes = nodes;
    this.predecessors = list;
  }

  public static PredecessorAdjacencyList fromGraph(final Graph graph) {
    ArrayList<Node> nodes = graph.getNodes();
    ArrayList<ArrayList<Node>> list = new ArrayList<>(nodes.size());

    for (Node node : nodes) {
      final ArrayList<Edge> edges = graph.getEdges();
      ArrayList<Node> originNodes = new ArrayList<>(edges.stream().filter(edge -> edge.destination == node)
          .map(edge -> edge.origin).collect(Collectors.toList()));

      list.add(originNodes);
    }

    return new PredecessorAdjacencyList(nodes, list);
  }

  public final ArrayList<Node> getNodes() {
    return this.nodes;
  }

  public final ArrayList<ArrayList<Node>> getPredecessors() {
    return this.predecessors;
  }

  public final int getEntryDegree(final int name) {
    return this.getPredecessorNodes(name).size();
  }

  public final ArrayList<Node> getPredecessorNodes(final int name) {
    return this.predecessors.get(name - 1);
  }
}
