public class Main {
  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    Graph graph = (Graph) Graph.fromRandomEdges(100_000, 1_000_000, Graph::new);
    System.out.println(graph);

    graph.saveGraph("100_000-nodes.txt");
    System.out.println((System.currentTimeMillis() - start) / (1_000 * 60));
  }
}
