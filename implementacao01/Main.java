import java.io.File;
import java.util.Scanner;

/**
 * Main
 */
public class Main {

  public static void main(String[] args) {
    Scanner fileScanner = null;
    Scanner inputScanner = new Scanner(System.in);
    try {
      System.out.println("Informe o nome do arquivo:");
      fileScanner = new Scanner(new File(inputScanner.nextLine()));
      Graph graph = Graph.fromScanner(fileScanner);

      System.out.println("Informe o vértice desejado: ");
      int selectedNode = inputScanner.nextInt();

      ReverseStar reverseStar = ReverseStar.fromGraph(graph);
      System.out.println("Grau de Entrada: " + reverseStar.getEntryDegree(selectedNode));
      reverseStar.printPredecessorNodes(selectedNode);

      ForwardStar forwardStar = ForwardStar.fromGraph(graph);
      System.out.println("Grau de Saída: " + forwardStar.getExitDegree(selectedNode));
      forwardStar.printSucessorNodes(selectedNode);

    } catch (Exception e) {
      System.err.println(e);
    } finally {
      inputScanner.close();
      fileScanner.close();
    }

  }
}