import java.util.ArrayList;

public final class Node {
  public final int name;

  public Node(final int name) {
    this.name = name;
  }

  /**
   * Creates a node array with a null header.
   * 
   * @param numberOfNodes
   * @return array of nodes.
   * @throws NegativeArraySizeException
   */
  public static ArrayList<Node> createNodes(final int numberOfNodes) throws NegativeArraySizeException {
    if (numberOfNodes < 0) {
      throw new NegativeArraySizeException("Number of nodes should be a natural number.");
    }

    ArrayList<Node> nodes = new ArrayList<>(numberOfNodes);
    for (int i = 1; i <= numberOfNodes; ++i) {
      nodes.add(new Node(i));
    }

    return nodes;
  }

  @Override
  public final String toString() {
    return this.name + "";
  }
}
