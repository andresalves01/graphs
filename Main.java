import java.util.Set;

public class Main {

  public static void main(String[] args) throws Exception {
    // Instancia o grafo com lista de adjacência
    AdjacencyList adjacencyList = new AdjacencyList(6);

    adjacencyList.insertEdge(0, 1);
    adjacencyList.insertEdge(1, 2);
    adjacencyList.insertEdge(2, 0);
    adjacencyList.insertEdge(1, 3);
    adjacencyList.insertEdge(3, 4);
    adjacencyList.insertEdge(4, 5);

    // Encontra e exibe as articulações no grafo
    System.out.println("\nArticulações encontradas:");
    adjacencyList.encontrarArticulacoes();
  }
}
