import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

class Graph {
    // [=================== Attributes ===================]

    private Node[] adjacencyList;
    private boolean isDirected;
    private int incidencyMatrix[][];
    private int V; // No. vertices
    private char[] vertices;
    private int E; // No. edges

    // [=================== Constructors And Initialization Methods
    // ===================]

    public Graph(String s) {
        String[] nodeVecs = s.replace(" ", "").replace("(", "").replace(")", "").split(",");
        this.isDirected = verifyType(nodeVecs);
        V = 0;
        E = 0;

        // creation methods
        createAdjacencyList(nodeVecs);
        connectNodes(nodeVecs, isDirected);
        sortList();
        vertices = new char[adjacencyList.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            vertices[i] = adjacencyList[i].getData();
        }

    }

    private void createAdjacencyList(String[] nodeVecs) {
        // count vertex
        String n = "";
        for (int i = 0; i < nodeVecs.length; i++) {
            try {
                Integer.parseInt(nodeVecs[i]);
            } catch (Exception e) {
                if (!n.contains(nodeVecs[i]))
                    n += nodeVecs[i];
            }
        }

        this.V = n.length();

        // create vec list
        adjacencyList = new Node[n.length()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new Node(n.charAt(i));
        }
    }

    private boolean verifyType(String[] nodeVecs) {
        for (int i = 0; i < nodeVecs.length; i += 3) {
            for (int j = 0; j < nodeVecs.length; j += 3) {
                if (nodeVecs[i + 1].equals(nodeVecs[j]) && nodeVecs[i].equals(nodeVecs[j + 1]))
                    return true;
            }
        }
        return false;
    }

    private void connectNodes(String[] nodeVecs, boolean isDirected) {
        for (int i = 0; i < nodeVecs.length; i += 3) {
            int toConnect = getIndexOf(nodeVecs[i].charAt(0));
            if (toConnect != -1) {
                appendToEnd(adjacencyList[toConnect],
                        new Node(nodeVecs[i + 1].charAt(0), Integer.parseInt(nodeVecs[i + 2])));
                E++;
            }

            if (!isDirected) {
                toConnect = getIndexOf(nodeVecs[i + 1].charAt(0));
                if (toConnect != -1) {
                    appendToEnd(adjacencyList[toConnect],
                            new Node(nodeVecs[i].charAt(0), Integer.parseInt(nodeVecs[i + 2])));
                }
            }
        }
    }

    // [=================== Methods ===================]

    // Matrices methods

    public int[][] getAdjacencyMatrix() {
        int matrix[][] = new int[adjacencyList.length][adjacencyList.length];

        Node p;
        for (int i = 0; i < adjacencyList.length; i++) {
            p = adjacencyList[i].getNext();
            while (p != null) {
                matrix[i][getIndexOf(p.getData())] = 1;
                p = p.getNext();
            }
        }
        return matrix;
    }

    public int[][] getWeightedAdjacencyMatrix() {
        int matrix[][] = new int[adjacencyList.length][adjacencyList.length];

        Node p;
        for (int i = 0; i < adjacencyList.length; i++) {
            p = adjacencyList[i].getNext();
            while (p != null) {
                matrix[i][getIndexOf(p.getData())] = p.getWeight();
                p = p.getNext();
            }
        }
        return matrix;
    }

    public int[][] getIncidencyMatrix() {
        incidencyMatrix = new int[V][E];
        HashMap<Integer, Character[]> edgesNames = edgesNames();
        for (Integer i = 0; i < edgesNames.size(); i++) {
            int aIndex = getIndexOf(edgesNames.get(i + 1)[0]);
            int bIndex = getIndexOf(edgesNames.get(i + 1)[1]);

            incidencyMatrix[aIndex][i] = 1;
            incidencyMatrix[bIndex][i] = isDirected ? -1 : 1;
        }
        return incidencyMatrix;
    }

    public HashMap<Integer, Character[]> edgesNames() {

        HashMap<Integer, Character[]> edgeNames = new HashMap();

        for (int i = 0; i < adjacencyList.length; i++) {
            Node p = adjacencyList[i];
            char aux = p.getData();
            // search existance (not directed)
            p = p.getNext();
            while (p != null) {
                if (!isDirected) {
                    boolean exists = false;
                    for (Character[] edgeValues : edgeNames.values()) {
                        if (edgeValues[0] == p.getData() && edgeValues[1] == aux) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists)
                        edgeNames.put(edgeNames.size() + 1, new Character[] { aux, p.getData() });
                } else {
                    edgeNames.put(edgeNames.size() + 1, new Character[] { aux, p.getData() });
                }
                p = p.getNext();
            }
        }

        if (edgeNames.size() != E)
            System.out.println("DIFERENTES");

        return edgeNames;
    }

    // Search methods

    public String BFS(char origin, char target) {
        Queue<Character> queue = new LinkedList<>();
        queue.add(origin);
        String s = "";
        Node p;
        char current;
        while (!queue.isEmpty()) {
            current = queue.poll();
            
            s += s.contains(current +"") ? "" : current +"";
            if (current == target)
            return s;
            
            p = adjacencyList[getIndexOf(current)].getNext();
            while (p != null) {
                // System.out.println(queue.toString() + " -> " + (queue.isEmpty() ? "Empty" :
                // "Elements In"));
                if (!queue.contains(p.getData()) && p.getData() != origin)
                    queue.add(p.getData());
                p = p.getNext();
            }
        }
        return s;
    }

    public void bellmanFord(int[][] graph, int numNodes, int startNode) {
        int[] distance = new int[numNodes];

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[startNode] = 0;

        for (int i = 0; i < numNodes - 1; i++) {
            for (int u = 0; u < numNodes; u++) {
                for (int v = 0; v < numNodes; v++) {
                    if (graph[u][v] != 0 && distance[u] != Integer.MAX_VALUE && distance[u] + graph[u][v] < distance[v]) {
                        distance[v] = distance[u] + graph[u][v];
                    }
                }
            }
        }

        for (int u = 0; u < numNodes; u++) {
            for (int v = 0; v < numNodes; v++) {
                if (graph[u][v] != 0 && distance[u] != Integer.MAX_VALUE && distance[u] + graph[u][v] < distance[v]) {
                    System.out.println("El grafo contiene un ciclo de peso negativo.");
                    return;
                }
            }
        }

        System.out.println("Nodo\tDistancia desde el Nodo Inicial");
        for (int i = 0; i < numNodes; i++) {
            System.out.println(i + "\t\t" + distance[i]);
        }

    }
    
    public String DFS(char start) {
        return DFS(adjacencyList[getIndexOf(start)].getData(), new boolean[adjacencyList.length]);
    }

    private String DFS(char v, boolean[] visited) {
        String s = v + "";
        int w = getIndexOf(v);
        visited[w] = true;
        Node p = adjacencyList[w];
        while (p != null) {
            w = getIndexOf(p.getData());
            if (!visited[w]) {
                s += DFS(p.getData(), visited);
            }
            p = p.getNext();
        }

        return s;
    }

    // dijkstra methods

    public Object[] dijkstra(char start, char target) {
        Object[] table = dijkstra(getIndexOf(start), getIndexOf(target));

        return table;
    }

    private Object[] dijkstra(int startIndex, int targetIndex) {
        Object[][][] table = new Object[V][V][2];
        // table[vertice][vertice][distance, verticename]

        ArrayList<Integer> discartedRows = new ArrayList<>();
        int currentIndex = startIndex;
        boolean founded = false;
        char targetChar = adjacencyList[targetIndex].getData();

        String s = "";
        int minDistance = 0;

        ArrayList<int[]> minValues = null; // [index, wigth, char]

        for (int i = 0; i < adjacencyList.length; i++) {
            if (i > 0) {
                for (int j = 0; j < (minValues != null ? minValues.size() : 1); j++) {
                    if (!discartedRows.contains(minValues.get(j)[0])) {

                        table[minValues.get(j)[0]][i][0] = i > 0 ? minValues.get(j)[1] : 0; //
                        table[minValues.get(j)[0]][i][1] = i > 0 ? (char) minValues.get(j)[2]
                                : adjacencyList[currentIndex].getData(); // char
                    }
                }
            } else {
                table[currentIndex][i][0] = 0; //
                table[currentIndex][i][1] = adjacencyList[currentIndex].getData(); // char
            }

            discartedRows.add(currentIndex);
            minValues = null; // delete values

            // Search connections of current node

            Node p = adjacencyList[currentIndex].getNext();
            while (p != null) {
                int pIndex = getIndexOf(p.getData());
                if (!discartedRows.contains(pIndex)) {
                    table[pIndex][i][0] = p.getWeight() + Integer.parseInt(table[currentIndex][i][0] + "");
                    table[pIndex][i][1] = adjacencyList[currentIndex].getData();
                }

                p = p.getNext();
            }

            // continue with minWeigth

            int minWeigth = Integer.MAX_VALUE;
            for (int j = 1; j < table.length; j++) {
                if (table[j][i][0] != null) {
                    int value = Integer.parseInt(table[j][i][0] + "");

                    if (value < minWeigth && !discartedRows.contains(j)) {
                        minWeigth = value;
                    }
                }
            }

            // verificar si existe otro valor minimo igual

            minValues = new ArrayList<int[]>();
            for (int j = 1; j < table.length; j++) {
                if (table[j][i][0] != null) {
                    int value = Integer.parseInt(table[j][i][0] + "");

                    if (value == minWeigth && !discartedRows.contains(j)) {
                        int[] minInfo = { j, value, (char) table[j][i][1] };
                        minValues.add(minInfo);
                    }
                }

            }

            if (currentIndex == targetIndex)
                founded = true;
            if (!founded) {
                s += !s.contains("" + table[currentIndex][i][1]) ? "" + table[currentIndex][i][1] : "";
                minDistance = (int) table[currentIndex][i][0];
            } else if (founded) {
                s += !s.contains(targetChar + "") ? "" + targetChar : "";
                minDistance = (int) table[currentIndex][i][0];
            }
            if (minValues.size() != 0)
                currentIndex = minValues.get(0)[0];

        }
        return new Object[] { table, s, minDistance };
    }

    public String shortestWay(char start, char end) {
        return (String) dijkstra(start, start)[1];
    }

    public int minDistance(char start, char end) {
        return (int) dijkstra(start, start)[2];
    }

    public Object[][][] getTable(char start, char end) {
        return (Object[][][]) dijkstra(start, end)[0];
    }

    // [=================== Utitly ===================]

    public int getIndexOf(char c) {
        for (int i = 0; i < adjacencyList.length; i++) {
            if (adjacencyList[i].getData() == c)
                return i;
        }
        return -1;
    }

    private void appendToEnd(Node start, Node x) {
        if (start != null) {
            Node p = start;
            while (p.getNext() != null) {
                p = p.getNext();
            }
            p.setNext(x);
        }
    }

    public String showAdjacencyList() {
        String s = "";
        Node p;
        for (int i = 0; i < adjacencyList.length; i++) {
            p = adjacencyList[i];
            s += "[" + p.getData() + "]" + (p.getNext() != null ? " -> " : " / ");
            p = p.getNext();
            while (p != null) {
                s += "(" + p.getData() + ", " + p.getWeight() + ")" + (p.getNext() != null ? " -> " : " / ");
                p = p.getNext();
            }
            s += "\n";
        }

        return s;
    }

    public void sortList() {
        for (int x = 0; x < adjacencyList.length; x++) {
            for (int y = 0; y < adjacencyList.length - 1; y++) {
                Node elementoActual = adjacencyList[y],
                        elementoSiguiente = adjacencyList[y + 1];
                if (elementoActual.getData() > elementoSiguiente.getData()) {
                    // Intercambiar
                    adjacencyList[y] = elementoSiguiente;
                    adjacencyList[y + 1] = elementoActual;
                }
            }
        }
    }

    public String showTable(Object[][][] table) {
        String s = "";
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                String ss = table[i][j][0] + ", " + table[i][j][1];
                s += (!ss.contains("null") ? ss : "----") + "\t";
            }
            s += "\n";
        }
        return s;
    }

    // [=================== Getters and Setters ===================]

    public Node[] getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(Node[] adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public int getV() {
        return V;
    }

    public char[] getVertices() {
        return vertices;
    }

    public int getE() {
        return E;
    }
}