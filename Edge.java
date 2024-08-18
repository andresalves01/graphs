public final class Edge {
  public final Node origin;
  public final Node destination;

  public Edge(final Node origin, final Node destination) {
    this.origin = origin;
    this.destination = destination;
  }

  @Override
  public final String toString() {
    return "(" + this.origin.toString() + ", " + this.destination.toString() + ")";
  }
}
