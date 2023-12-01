public class App {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph("((Z,C,1),(Z,F,2),(Z,E,3),(F,E,1),(F,C,1),(E,D,2),(C,B,5),(D,G,1),(D,A,5),(G,A,2),(G,B,1),(B,A,4))");
        System.out.println("Is Directed -> "+ graph.isDirected());
        System.out.println(graph.showAdjacencyList());

        System.out.println("V: " +  graph.getV() + "\nE: " + graph.getE());


        int[][] matrix = graph.getWeightedAdjacencyMatrix();
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(graph.getAdjacencyList()[i].getData()+" ");
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }

        System.out.println("\n\n\n");

        int[][] incidencyMatrix = graph.getIncidencyMatrix();
        for (int i = 0; i < incidencyMatrix.length; i++) {
            System.out.print(graph.getAdjacencyList()[i].getData()+" ");
            for (int j = 0; j < incidencyMatrix[0].length; j++) {
                System.out.print(incidencyMatrix[i][j]+" ");
            }
            System.out.println();
        }

        Object[] dijkstra = graph.dijkstra('A', 'Z');
        System.out.println(graph.showTable((Object[][][]) dijkstra[0]));
        System.out.println("\n-> " + dijkstra[1] + " " + dijkstra[2]);

        System.out.println("\nSearch BFS -> " + graph.BFS('A', 'Z'));
        System.out.println("Search DFS -> " + graph.DFS('A'));

        showGraph pg = new showGraph(graph.getWeightedAdjacencyMatrix(), graph.getVertices());
        pg.showGraphInterface(matrix);
    }
}