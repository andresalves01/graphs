import java.io.File;
import java.util.Scanner;

/**
 * Main
 */
public class Main {

  public static void main(String[] args) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File("graph-test-100-1.txt"));
      Graph graph = Graph.fromScanner(scanner);
      System.out.println(graph.toString());
    } catch (Exception e) {
      System.err.println(e);
    } finally {
      if (scanner != null) {
        scanner.close();
      }
    }

  }
}