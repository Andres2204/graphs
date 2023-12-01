import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Graph {
    // [=================== Attributes ===================]

    private Node[] adjacencyList;
    private boolean isDirected;
    private int incidencyMatrix[][] = new int[3][3];
    private int V; // No. vertices

    // [=================== Constructors And Initialization Methods
    // ===================]

    public Graph(String s) {
        String[] nodeVecs = s.replace(" ", "").replace("(", "").replace(")", "").split(",");
        this.isDirected = verifyType(nodeVecs);
        createAdjacencyList(nodeVecs);
        connectNodes(nodeVecs, isDirected);
        sortList();
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
            if (toConnect != -1)
                appendToEnd(adjacencyList[toConnect],
                        new Node(nodeVecs[i + 1].charAt(0), Integer.parseInt(nodeVecs[i + 2])));

            if (!isDirected) {
                toConnect = getIndexOf(nodeVecs[i + 1].charAt(0));
                if (toConnect != -1)
                    appendToEnd(adjacencyList[toConnect],
                            new Node(nodeVecs[i].charAt(0), Integer.parseInt(nodeVecs[i + 2])));
            }
        }
    }

    // [=================== Methods ===================]

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

    public void getIncidencyMatrix() {
        System.out.println("Incidency Matrix: ");


        for (int i = 0; i < adjacencyList.length; i++) {
            for (int j = 0; j < adjacencyList.length; j++) {
                System.out.print(incidencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
        // return incidencyMatrix;
    }

    public boolean BFS(char origin, char target) {
        Queue<Character> queue = new LinkedList<>();
        queue.add(origin);

        Node p;
        char current;
        while (!queue.isEmpty()) {
            current = queue.poll();

            if (current == target)
                return true;

            p = adjacencyList[getIndexOf(current)].getNext();
            while (p != null) {
                // System.out.println(queue.toString() + " -> " + (queue.isEmpty() ? "Empty" :
                // "Elements In"));
                if (!queue.contains(p.getData()) && p.getData() != origin)
                    queue.add(p.getData());
                p = p.getNext();
            }
        }
        return false;
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
    
    // shortest way

    public void dijkstra(char start, char target) {
        dijkstra(getIndexOf(start), getIndexOf(target));
    }

    public Object[][][] dijkstra(int startIndex, int targetIndex) {
        Object[][][] table = new Object[V][V][2];

        ArrayList<Integer> discartedRows = new ArrayList<>();
        String s ="";
        int minDistance=0;

        // table[vertice][vertice][distance, verticename]
        int currentIndex = startIndex;
        ArrayList<int[]> minValues = null; // [index, wigth, char]

        for (int i = 0; i < adjacencyList.length; i++) {
            if ( i > 0 ) {
                for (int j = 0; j < (minValues != null ? minValues.size() : 1); j++) {
                    if (!discartedRows.contains(minValues.get(j)[0])) {

                        table[minValues.get(j)[0]][i][0] = i > 0 ? minValues.get(j)[1] : 0; //
                        table[minValues.get(j)[0]][i][1] = i > 0 ? (char) minValues.get(j)[2] : adjacencyList[currentIndex].getData(); // char
                    }
                }
            } else {
                table[currentIndex][i][0] = 0; //
                table[currentIndex][i][1] = adjacencyList[currentIndex].getData(); // char
            }

            s+= (char) table[currentIndex][i][1];

            discartedRows.add(currentIndex);
            
            minValues = null; // delete values

            // Search connections of current node

            Node p = adjacencyList[currentIndex].getNext();
            // TODO: Hacer una vuelta completa verifificando incluso lo que no deberia suceder
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

            // TODO: Pasar lo valores que no sean minimos, siempre y cuando la linea no este descartada.

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
            if (minValues.size() != 0) currentIndex = minValues.get(0)[0];
            minDistance = (int) table[currentIndex][i][0];

        }

        System.out.println("Distancia: " + minDistance);
        System.out.println("Recorrido: " + s);

        // show table (temp)
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                String ss = table[i][j][0] + ", " + table[i][j][1];
                System.out.print((!ss.contains("null") ? ss : "----") + "\t");
            }
            System.out.println();
        }

        return table;
    }

    public void shortestWay() {

    }

    // [=================== Utitly ===================]

    // TODO: ver si es necesario
    // public char[] edgesWith(char c) {
    // int ci = getIndexOf(c);

    // for (int i = 0; i < adjacencyList.length; i++) {
    // if (adjacencyList[i].getData() == c) continue;
    // Node p = adjacencyList[i].getNext()

    // while(p != null) {}
    // }
    // }

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
            int i = 0;
            int j = 0;
            while (p.getNext() != null) {
                p = p.getNext();
                this.incidencyMatrix[i++][j++] = 1;
                this.incidencyMatrix[i++][j++] = 1;
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
}