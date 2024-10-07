import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CicleSearcher {
  private final List<Set<Integer>> components = new LinkedList<>();

  public List<Set<Integer>> getComponents() {
    return components;
  }

  public CicleSearcher(Graph graph) {
    int nodeSize = graph.getNodes().size();
    for (Integer i = 0; i < nodeSize - 1; i++)
      for (Integer j = i + 1; j < nodeSize; j++) {
        if (alreadyPartOfComponent(i, j)) {
          continue;
        }

        Set<Integer> cicle = searchCicle(i, j, graph);
        if (cicle.isEmpty()) {
          continue;
        }
        int oldCicleSize = cicle.size();

        cicle = concatWithOtherCicles(cicle);
        while (oldCicleSize != cicle.size()) {
          oldCicleSize = cicle.size();
          cicle = concatWithOtherCicles(cicle);
        }

        components.add(cicle);
      }
  }

  private static Set<Integer> getCicle(Stack<Node> stack, Integer nextNode) {
    Set<Integer> arrayList = new HashSet<>(stack.size() + 1);
    arrayList.add(nextNode);
    for (Node i = stack.pop(); !stack.isEmpty(); i = stack.isEmpty() ? null : stack.pop()) {
      arrayList.add((Integer) i.value);
    }

    return arrayList;
  }

  private boolean alreadyPartOfComponent(Integer a, Integer b) {
    for (Set<Integer> set : components) {
      if (set.contains(a) && set.contains(b)) {
        return true;
      }
    }

    return false;
  }

  private static boolean mustConcatCicles(Set<Integer> componentOne, Set<Integer> componentTwo) {
    int repeatedNodesCount = 0;
    for (Integer integer : componentOne) {
      if (componentTwo.contains(integer)) {
        repeatedNodesCount++;
      }

      if (repeatedNodesCount >= 2) {
        return true;
      }
    }

    return false;
  }

  private Set<Integer> concatWithOtherCicles(Set<Integer> cicle) {
    for (int k = 0; k < components.size(); k++) {
      Set<Integer> component = components.get(k);
      if (CicleSearcher.mustConcatCicles(cicle, component)) {
        cicle = Stream.concat(cicle.stream(), component.stream())
            .collect(Collectors.toSet());
        components.remove(k);
      }
    }

    return cicle;
  }

  private class Node {
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

  private Set<Integer> searchCicle(int origin, int destination, Graph graph) {
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
          return getCicle(stack, nextNode);
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

    return Set.of();
  }

  public void printCicles() {
    for (Set<Integer> component : components) {
      for (Integer integer : component) {
        System.out.print(integer + " ");
      }
      System.out.println();
    }
  }
}
