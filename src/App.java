public class App {
    public static void main(String[] args) throws Exception {
        // TODO: Validar si el string que se va a ingresar es de un diriguido o no
        Graph graph = new Graph("((A,B,2),(A,C,5),(B,A,2))");
        System.out.println("Is Directed -> "+ graph.isDirected());
        System.out.println(graph.showAdjacencyList());


        int[][] matrix = graph.getAdjacencyMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }


        System.out.println("Search -> " + graph.BFS('A', 'Z'));
    }
}

