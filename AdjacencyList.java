import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/**
 * AdjacencyList
 */
public class AdjacencyList extends Graph {

  private final Map<Integer, List<Integer>> adjacents = new HashMap<>();

  public AdjacencyList(int numberOfNodes) {
    this(numberOfNodes, 0);
  }

  public AdjacencyList(int numberOfNodes, int numberOfEdges) {
    super(numberOfNodes);
  }

  @Override
  public boolean insertEdge(int origin, int destination) {
    List<Integer> originAdjacents = this.adjacents.get(origin);
    List<Integer> destinationAdjacents = this.adjacents.get(destination);

    if (originAdjacents == null && this.contains(origin)) {
      originAdjacents = new LinkedList<>();
      this.adjacents.put(origin, originAdjacents);
    }

    if (destinationAdjacents == null && this.contains(destination)) {
      destinationAdjacents = new LinkedList<>();
      this.adjacents.put(destination, destinationAdjacents);
    }

    if (originAdjacents.contains(destination) || destinationAdjacents.contains(origin)) {
      return false;
    }

    return originAdjacents.add(destination) && destinationAdjacents.add(origin);
  }

  @Override
  public List<Integer> getAdjacentNodes(int node) throws NoSuchElementException {
    if (!this.adjacents.containsKey(node)) {
      throw new NoSuchElementException("The specified node is not present in the graph.");
    }

    return this.adjacents.get(node);
  }

  @Override
  public List<Edge> getEdges() {
    List<Edge> edges = new LinkedList<>();
    for (Entry<Integer, List<Integer>> node : this.adjacents.entrySet()) {
      Integer origin = node.getKey();
      for (Integer destination : node.getValue()) {
        if (origin < destination) {
          edges.add(new Edge(origin, destination));
        }
      }
    }

    return edges;
  }

  @Override
  public boolean removeEdge(int nodeOne, int nodeTwo) {
    List<Integer> nodeOneAdjacents = this.adjacents.get(nodeOne);
    List<Integer> nodeTwoAdjacents = this.adjacents.get(nodeTwo);
    if (nodeOneAdjacents == null || nodeTwoAdjacents == null) {
      return false;
    }

    return nodeOneAdjacents.remove(nodeTwo) != null && nodeTwoAdjacents.remove(nodeOne) != null;
  }

}