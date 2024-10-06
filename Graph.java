import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.function.BiFunction;

public abstract class Graph {
    private final Set<Integer> nodes;
    private int time = 0;

    public Graph(int nodeSize) {
        this.nodes = new HashSet<Integer>(nodeSize);
        for (int i = 0; i < nodeSize; i++) {
            this.insertNode(i);
        }
    }

    public Graph(Set<Integer> nodes) {
        this.nodes = nodes;
    }

    public boolean insertNode(int node) {
        return nodes.add(node);
    }

    public final Set<Integer> getNodes() {
        return nodes;
    }

    public final boolean contains(int node) {
        return nodes.contains(node);
    }

    public final boolean removeNode(int node) {
        removeEdges(node);
        return nodes.remove(node);
    }

    public abstract boolean insertEdge(int origin, int destination);

    public abstract List<Edge> getEdges();

    public abstract List<Integer> getAdjacentNodes(int destination) throws NoSuchElementException;

    public abstract boolean removeEdge(int origin, int destination);

    public final void removeEdges(int node) {
        List<Integer> adjacentNodes = this.getAdjacentNodes(node);
        for (int origin : adjacentNodes) {
            removeEdge(origin, node);
        }
    }

    public final void saveGraph(String filename) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            List<Edge> edges = getEdges();
            writer.println(nodes.size() + " " + edges.size());

            for (Edge edge : edges) {
                writer.println(edge.origin + " " + edge.destination);
            }
        }
    }

    public static Graph fromFile(String fileName, BiFunction<Integer, Integer, Graph> constructor)
            throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            int nodesSize = scanner.nextInt();
            int edgesSize = scanner.nextInt();
            Graph graph = constructor.apply(nodesSize, edgesSize);

            for (int i = 0; i < edgesSize; i++) {
                int origin = scanner.nextInt();
                int destination = scanner.nextInt();

                graph.insertEdge(origin, destination);
            }

            return graph;
        }
    }

    public static Graph fromRandomEdges(int numberOfNodes, int numberOfEdges,
            BiFunction<Integer, Integer, Graph> constructor) throws Exception {
        Graph.validateEdgeNumber(numberOfNodes, numberOfEdges);
        Graph graph = constructor.apply(numberOfNodes, numberOfEdges);

        int maxNumberOfEdges = (int) Math.ceil(((numberOfNodes * numberOfEdges) - numberOfEdges) * 0.5);
        if (numberOfEdges == maxNumberOfEdges) {
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = i + 1; j < numberOfNodes; j++) {
                    graph.insertEdge(i, j);
                }
            }

            return graph;
        }

        while (graph.getEdges().size() < numberOfEdges) {
            int origin = (int) (Math.random() * numberOfNodes);
            int destination = (int) (Math.random() * numberOfNodes);
            if (origin == destination) {
                continue;
            }

            boolean originLessThanDestination = origin < destination;
            graph.insertEdge(originLessThanDestination ? origin : destination,
                    originLessThanDestination ? destination : origin);
        }

        return graph;
    }

    @Override
    public String toString() {
        return "V = {" + nodes.stream().map((node) -> node.toString()).collect(Collectors.joining(", ")) + "}\n" +
                "E = {"
                + getEdges().stream().map((edge) -> "{" + edge.origin + ", " + edge.destination + "}")
                .collect(Collectors.joining(", "))
                + "}";
    }

    public static void validateEdgeNumber(int numberOfNodes, int numberOfEdges) throws Exception {
        int maxNumberOfEdges = (int) Math.ceil(((numberOfNodes * numberOfNodes) - numberOfNodes) * 0.5);
        if (numberOfEdges > maxNumberOfEdges) {
            throw new Exception("O número de arestas ultrapassa o número máximo permitido.");
        }
    }

    // Método para encontrar e exibir articulações
    public void encontrarArticulacoes() {
        boolean[] visited = new boolean[nodes.size()];
        int[] discoveryTime = new int[nodes.size()];
        int[] low = new int[nodes.size()];
        int[] parent = new int[nodes.size()];
        boolean[] isArticulation = new boolean[nodes.size()];

        Arrays.fill(parent, -1); // Inicializa o array de pais com -1

        for (int node : nodes) {
            if (!visited[node]) {
                encontrarArticulacoesUtil(node, visited, discoveryTime, low, parent, isArticulation);
            }
        }

        // Exibe as articulações encontradas
        System.out.println("Articulações no grafo:");
        for (int i = 0; i < nodes.size(); i++) {
            if (isArticulation[i]) {
                System.out.println("Vértice " + i);
            }
        }
    }

    // Método utilitário recursivo para encontrar articulações usando DFS
    private void encontrarArticulacoesUtil(int u, boolean[] visited, int[] discoveryTime, int[] low, int[] parent, boolean[] isArticulation) {
        int filhos = 0;
        visited[u] = true;
        discoveryTime[u] = low[u] = ++time;

        for (int v : getAdjacentNodes(u)) {
            if (!visited[v]) {
                filhos++;
                parent[v] = u;
                encontrarArticulacoesUtil(v, visited, discoveryTime, low, parent, isArticulation);

                low[u] = Math.min(low[u], low[v]);

                if (parent[u] == -1 && filhos > 1) {
                    isArticulation[u] = true;
                }

                if (parent[u] != -1 && low[v] >= discoveryTime[u]) {
                    isArticulation[u] = true;
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], discoveryTime[v]);
            }
        }
    }
}
