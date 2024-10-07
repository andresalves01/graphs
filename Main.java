public class Main {

  public static void main(String[] args) throws Exception {
    Graph graph = Graph.fromRandomEdges(30, 50, AdjacencyList::new);
    CicleSearcher foo = new CicleSearcher(graph);

    // for (int i = 0; i < 4; i++) {
    // int numberOfNodes = 100 * (int) Math.pow(10, i);
    // for (int j = 1; j <= 4; j++) {
    // String fileName = "./" + numberOfNodes + "-nodes/" + j + ".txt";
    // System.out.println("Lendo: " + fileName);

    // Graph graph = Graph.fromFile(fileName, AdjacencyList::new);
    // new CicleSearcher(graph);
    // }
    // }
    // graph.encontrarArticulacoes();
    // new Tarjan(graph);
  }
}
