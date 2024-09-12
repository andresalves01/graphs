import java.util.*;

class Node {
  public int value;
  public int nextIndex = 0;

  public Node(int value) {
    this.value = value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public boolean equals(Object that) {
    return ((int) that) == this.value;
  }
}

public class CicleSearcher {
  public static boolean searchCicle(int origin, int destination, Graph graph) {
    Stack<Node> stack = new Stack<>();
    Set<Integer> visitedNodes = new HashSet<>();

    Node originNode = new Node(origin);
    stack.push(originNode);
    visitedNodes.add(origin);

    boolean inbound = false;
    for (Node stackNode = stack.peek(); stackNode != null; stackNode = !stack.isEmpty() ? stack.peek()
        : null) {
      List<Integer> adjacents = graph.getAdjacentNodes(stackNode.value);

      Integer nextNode = stackNode.nextIndex + 1 <= adjacents.size() ? adjacents.get(stackNode.nextIndex++) : null;
      while (nextNode != null && visitedNodes.contains(nextNode)) {
        if (nextNode == origin && inbound) {
          return true;
        }

        nextNode = stackNode.nextIndex + 1 <= adjacents.size() ? adjacents.get(stackNode.nextIndex++) : null;
      }

      if (nextNode == null) {
        stack.pop();
        visitedNodes.remove(stackNode.value);

        if (stackNode.value == destination) {
          inbound = false;
        }

        continue;
      }

      if (nextNode == destination) {
        inbound = true;
      }

      stack.push(new Node(nextNode));
      visitedNodes.add(nextNode);
    }

    return false;
  }
}
