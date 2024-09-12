public class Main {
  public static void main(String[] args) throws Exception {
    AdjacencyList adjacencyList = new AdjacencyList(6, 8);

    adjacencyList.insertEdge(0, 1);
    adjacencyList.insertEdge(0, 5);
    adjacencyList.insertEdge(1, 2);
    adjacencyList.insertEdge(1, 4);
    adjacencyList.insertEdge(1, 5);
    adjacencyList.insertEdge(2, 3);
    adjacencyList.insertEdge(2, 5);
    adjacencyList.insertEdge(3, 4);

    System.out.println(CicleSearcher.searchCicle(0, 3, adjacencyList));
  }
}
