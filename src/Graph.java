import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Graph {
    // [=================== Attributes ===================]
    
    private Node[] adjacencyList;
    private boolean isDirected;
    private int incidencyMatrix[][] = new int[3][3];
    private int V; // No. vertices

    // [=================== Constructors And Initialization Methods ===================]

    public Graph(String s) {
        String[] nodeVecs = s.replace("(", "").replace(")", "").split(",");
        this.isDirected = verifyType(nodeVecs);
        createAdjacencyList(nodeVecs);
        connectNodes(nodeVecs, isDirected);

    }

    private void createAdjacencyList(String[] nodeVecs) {
        // count vertex
        String n = "";
        for (int i = 0; i < nodeVecs.length; i++) {
            try {Integer.parseInt(nodeVecs[i]);}
            catch(Exception e) {
                if (!n.contains(nodeVecs[i])) n += nodeVecs[i];
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
        for (int i = 0; i < nodeVecs.length; i+=3) {
            for (int j = 0; j < nodeVecs.length; j+=3) {
                if (nodeVecs[i+1].equals(nodeVecs[j]) && nodeVecs[i].equals(nodeVecs[j+1])) return true;
            }
        }
        return false;
    }

    private void connectNodes(String[] nodeVecs, boolean isDirected) {
        for (int i = 0; i < nodeVecs.length; i+=3) {
            int toConnect = getIndexOf(nodeVecs[i].charAt(0));
            if (toConnect != -1) appendToEnd(adjacencyList[toConnect], new Node(nodeVecs[i+1].charAt(0), Integer.parseInt(nodeVecs[i+2])));
            
            if (!isDirected) {
                toConnect = getIndexOf(nodeVecs[i+1].charAt(0));
                if (toConnect != -1) appendToEnd(adjacencyList[toConnect], new Node(nodeVecs[i].charAt(0), Integer.parseInt(nodeVecs[i+2])));
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
        while(!queue.isEmpty()) {
            current = queue.poll();

            if (current == target) return true;

            p = adjacencyList[getIndexOf(current)].getNext();
            while (p != null) {
                System.out.println(queue.toString() + " -> " + (queue.isEmpty() ? "Empty" : "Elements In"));
                if (!queue.contains(p.getData()) && p.getData() != origin) queue.add(p.getData());
                p = p.getNext();
            }
        }
        return false;
    }

    // [=================== Utitly ===================]

    public int getIndexOf(char c) {
        for (int i = 0; i < adjacencyList.length; i++) {
            if (adjacencyList[i].getData() == c) return i;
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
                s += p.getData() + (p.getNext() != null ? " -> " : " / ");
                p = p.getNext();
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
}