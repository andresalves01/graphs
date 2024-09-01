import java.util.Objects;

public class Edge {
  public final int origin;
  public final int destination;

  public Edge(int origin, int destination) {
    this.origin = origin;
    this.destination = destination;
  }

  @Override
  public final int hashCode() {
    return Objects.hash(this.origin ^ this.destination);
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass())
      return false;

    Edge that = (Edge) obj;
    return (this.origin == that.origin || this.origin == that.destination)
        && (this.destination == that.destination || this.destination == that.origin);
  }

  @Override
  public final String toString() {
    return "{" + this.origin + ", " + this.destination + "}";
  }

}
