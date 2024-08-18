import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FowardStar
 */
public final class ReverseStar {

  private final ArrayList<Node> nodes;
  private final int[] pointers;
  private final ArrayList<Node> origins;

  public ReverseStar(final ArrayList<Node> nodes, final int[] pointers, final ArrayList<Node> origins) {
    this.nodes = nodes;
    this.pointers = pointers;
    this.origins = origins;
  }

  public static ReverseStar fromGraph(final Graph graph) {
    ArrayList<Node> nodes = graph.getNodes();
    int[] pointers = new int[nodes.size() + 1];
    List<Edge> edges = graph.getEdges().stream().sorted((prev, curr) -> prev.destination.name - curr.destination.name)
        .collect(Collectors.toList());

    int nodeIndex = 0;
    Node cursor = nodes.get(0);

    int startingPosition = 0;
    pointers[0] = 0;

    for (int i = 0; i < edges.size(); ++i) {
      Node currentDestination = edges.get(i).destination;
      while (cursor != currentDestination && nodeIndex < nodes.size()) {
        pointers[++nodeIndex] = startingPosition;
        cursor = nodes.get(nodeIndex);
      }

      ++startingPosition;
    }

    pointers[pointers.length - 1] = startingPosition;

    return new ReverseStar(nodes, pointers,
        new ArrayList<Node>(edges.stream().map(edge -> edge.origin).collect(Collectors.toList())));
  }

  public final Node getNode(final int name) {
    return this.nodes.get(name - 1);
  }

  public final int getEntryDegree(final int name) {
    return pointers[name] - pointers[name - 1];
  }

  public final ArrayList<Node> getPredecessorNodes(final int name) {
    ArrayList<Node> predecessorNodes = new ArrayList<>(getEntryDegree(name));

    for (int i = pointers[name - 1]; i < pointers[name]; ++i) {
      predecessorNodes.add(origins.get(i));
    }

    return predecessorNodes;
  }

  public final void printPredecessorNodes(final int name) {
    for (Node node : getPredecessorNodes(name)) {
      System.out.print(node.name + " ");
    }
    System.out.println();
  }
}