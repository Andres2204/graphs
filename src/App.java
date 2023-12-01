public class App {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph("((Z,C,1),(Z,F,2),(Z,E,3),(F,E,1),(F,C,1),(E,D,2),(C,B,5),(D,G,1),(D,A,5),(G,A,2),(G,B,1),(B,A,4))");
        System.out.println("Is Directed -> "+ graph.isDirected());
        System.out.println(graph.showAdjacencyList());


        int[][] matrix = graph.getAdjacencyMatrix();
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(graph.getAdjacencyList()[i].getData()+" ");
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }

        Object[] dijkstra = graph.dijkstra('A', 'Z');
        System.out.println(graph.showTable((Object[][][]) dijkstra[0]));
        System.out.println("\n-> " + dijkstra[1] + " " + dijkstra[2]);

        System.out.println("\nSearch BFS -> " + graph.BFS('A', 'Z'));
        System.out.println(graph.DFS('A'));
    }
}