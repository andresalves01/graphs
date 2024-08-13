import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * FowardStar
 */
public class FowardStar {
  public ArrayList<Edge> edges;

  public FowardStar(ArrayList<Edge> edges) {
    this.edges = new ArrayList<Edge>(
        edges.stream().sorted((i, j) -> i.origin.name - j.origin.name).collect(Collectors.toList()));
  }

}