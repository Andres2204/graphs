class Graph {
    // [=================== Attributes ===================]
    
    private Node[] adjacencyList;
    boolean isDirected = false;

    // [=================== Constructors ===================]

    public Graph(String s, boolean isDirected) {
        String[] nodeVecs = s.replace("(", "").replace(")", "").split(",");
        this.isDirected = verifyDirection(nodeVecs);
        createAdjacencyList(nodeVecs);
        connectNodes(nodeVecs, isDirected);

    }

    public void createAdjacencyList(String[] nodeVecs) {
        // count vertex
        String n = "";
        for (int i = 0; i < nodeVecs.length; i++) {
            try {Integer.parseInt(nodeVecs[i]);}
            catch(Exception e) {
                if (!n.contains(nodeVecs[i])) n += nodeVecs[i];
            }
        }

        // create vec list
        adjacencyList = new Node[n.length()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new Node(n.charAt(i));
        }
    }

    // TODO: verificacion de si es un grafo diriguido o no
    public boolean verifyDirection(String[] nodeVecs) {

        return false;
    }

    private void connectNodes(String[] nodeVecs, boolean isDirected) {
        for (int i = 0; i < nodeVecs.length; i+=3) {
            int toConnect = getIndexOf(nodeVecs[i].charAt(0));
            if (toConnect != -1) appendToEnd(adjacencyList[toConnect], new Node(nodeVecs[i+1].charAt(0)));
            
            if (!isDirected) {
                toConnect = getIndexOf(nodeVecs[i+1].charAt(0));
                if (toConnect != -1) appendToEnd(adjacencyList[toConnect], new Node(nodeVecs[i].charAt(0)));
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
}