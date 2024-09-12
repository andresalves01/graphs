import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * AdjacencyList
 */
public class AdjacencyList implements Graph {

  private final Map<Integer, List<Integer>> adjacents;

  public AdjacencyList(int numberOfNodes) {
    this(numberOfNodes, 0);
  }

  public AdjacencyList(int numberOfNodes, int numberOfEdges) {
    this.adjacents = new HashMap<>(numberOfNodes);
    for (int i = 0; i < numberOfNodes; i++) {
      insertNode(i);
    }
  }

  public AdjacencyList(Map<Integer, List<Integer>> adjacents) {
    this.adjacents = adjacents;
  }

  @Override
  public boolean insertNode(int node) {
    return this.adjacents.putIfAbsent(node, new LinkedList<>()) != null;
  }

  @Override
  public boolean contains(int node) {
    return this.adjacents.containsKey(node);
  }

  @Override
  public Set<Integer> getNodes() {
    return this.adjacents.keySet();
  }

  @Override
  public boolean removeNode(int node) {
    return this.adjacents.remove(node) != null && this.removeEdges(node);
  }

  @Override
  public boolean insertEdge(int origin, int destination) {
    List<Integer> originAdjacents = this.adjacents.get(origin);
    List<Integer> destinationAdjacents = this.adjacents.get(destination);

    boolean hasNodes = originAdjacents != null && destinationAdjacents != null;
    boolean edgeAlreadyInserted = hasNodes
        && (originAdjacents.contains(destination) || destinationAdjacents.contains(origin));
    if (!hasNodes || edgeAlreadyInserted) {
      return false;
    }

    return originAdjacents.add(destination) && destinationAdjacents.add(origin);
  }

  @Override
  public Set<Edge> getEdges() {
    Set<Edge> edges = new HashSet<>();
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
  public List<Integer> getAdjacentNodes(int node) throws NoSuchElementException {
    if (!this.adjacents.containsKey(node)) {
      throw new NoSuchElementException("The specified node is not present in the graph.");
    }

    return this.adjacents.get(node);
  }

  @Override
  public boolean removeEdges(int node) {
    List<Integer> edges = this.adjacents.get(node);
    if (edges == null) {
      return false;
    }

    boolean removedEdges = true;
    for (Integer destination : edges) {
      removedEdges = removedEdges && removeEdge(node, destination);

    }

    return removedEdges;
  }

  @Override
  public boolean removeEdge(int nodeOne, int nodeTwo) {
    List<Integer> nodeOneAdjacents = this.adjacents.get(nodeOne);
    List<Integer> nodeTwoAdjacents = this.adjacents.get(nodeTwo);
    if (nodeOneAdjacents == null || nodeTwoAdjacents == null) {
      return false;
    }

    this.adjacents.put(nodeTwo, nodeTwoAdjacents.stream().filter(origin -> origin == nodeOne)
        .collect(Collectors.toList()));

    this.adjacents.put(nodeOne,
        nodeOneAdjacents.stream().filter(destination -> destination == nodeTwo).collect(Collectors.toList()));

    return true;
  }

}