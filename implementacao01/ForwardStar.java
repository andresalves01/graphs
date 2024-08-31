import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FowardStar
 */
public final class ForwardStar {

  private final ArrayList<Node> nodes;
  private final int[] pointers;
  private final ArrayList<Node> destination;

  public ForwardStar(final ArrayList<Node> nodes, final int[] pointers, final ArrayList<Node> destination) {
    this.nodes = nodes;
    this.pointers = pointers;
    this.destination = destination;
  }

  public static ForwardStar fromGraph(final Graph graph) {
    ArrayList<Node> nodes = graph.getNodes();
    int[] pointers = new int[nodes.size() + 1];
    List<Edge> edges = graph.getEdges().stream().sorted((prev, curr) -> prev.origin.name - curr.origin.name)
        .collect(Collectors.toList());

    int nodeIndex = 0;
    Node cursor = nodes.get(0);

    int startingPosition = 0;
    pointers[0] = 0;

    for (int i = 0; i < edges.size(); ++i) {
      Node currentOrigin = edges.get(i).origin;
      while (cursor != currentOrigin && nodeIndex < nodes.size()) {
        pointers[++nodeIndex] = startingPosition;
        cursor = nodes.get(nodeIndex);
      }

      ++startingPosition;
    }

    pointers[pointers.length - 1] = startingPosition;

    return new ForwardStar(nodes, pointers,
        new ArrayList<Node>(edges.stream().map(edge -> edge.destination).collect(Collectors.toList())));
  }

  public final Node getNode(final int name) {
    return this.nodes.get(name - 1);
  }

  public final int getExitDegree(final int name) {
    return pointers[name] - pointers[name - 1];
  }

  public final ArrayList<Node> getSuccessorNodes(final int name) {
    ArrayList<Node> succesorNodes = new ArrayList<>(getExitDegree(name));

    for (int i = pointers[name - 1]; i < pointers[name]; ++i) {
      succesorNodes.add(destination.get(i));
    }

    return succesorNodes;
  }

  public final void printSucessorNodes(final int name) {
    System.out.print("Sucessores: ");
    for (Node node : getSuccessorNodes(name)) {
      System.out.print(node.name + " ");
    }
    System.out.println();
  }

}