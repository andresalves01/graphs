import java.util.*;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

public class AdjacencyList extends Graph {
  private final Map<Integer, List<Integer>> adjacents = new HashMap<>();
  private int time = 0;

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

  // Método para encontrar e exibir articulações
  public void encontrarArticulacoes() {
    boolean[] visited = new boolean[getNodes().size()];
    int[] discoveryTime = new int[getNodes().size()];
    int[] low = new int[getNodes().size()];
    int[] parent = new int[getNodes().size()];
    boolean[] isArticulation = new boolean[getNodes().size()];

    Arrays.fill(parent, -1); // Inicializa o array de pais com -1

    for (int node : getNodes()) {
      if (!visited[node]) {
        encontrarArticulacoesUtil(node, visited, discoveryTime, low, parent, isArticulation);
      }
    }

    // Exibe as articulações encontradas
    System.out.println("Articulações no grafo:");
    for (int i = 0; i < getNodes().size(); i++) {
      if (isArticulation[i]) {
        System.out.println("Vértice " + i);
      }
    }
  }

  // Método utilitário recursivo para encontrar articulações usando DFS
  private void encontrarArticulacoesUtil(int u, boolean[] visited, int[] discoveryTime, int[] low, int[] parent,
      boolean[] isArticulation) {
    int filhos = 0;
    visited[u] = true;
    discoveryTime[u] = low[u] = ++time;

    for (int v : getAdjacentNodes(u)) {
      if (!visited[v]) {
        filhos++;
        parent[v] = u;
        encontrarArticulacoesUtil(v, visited, discoveryTime, low, parent, isArticulation);

        low[u] = Math.min(low[u], low[v]);

        if (parent[u] == -1 && filhos > 1) {
          isArticulation[u] = true;
        }

        if (parent[u] != -1 && low[v] >= discoveryTime[u]) {
          isArticulation[u] = true;
        }
      } else if (v != parent[u]) {
        low[u] = Math.min(low[u], discoveryTime[v]);
      }
    }
  }
}
