import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {



        int opt = 1;
        Scanner read = new Scanner(System.in);

        while (opt != 0) {

            try {

                System.out.println("\n[============== GRAFOS ==============]\n\n");
                Graph graph = new Graph("((Z,C,1),(Z,F,2),(Z,E,3),(F,E,1),(F,C,1),(E,D,2),(C,B,5),(D,G,1),(D,A,5),(G,A,2),(G,B,1),(B,A,4))");
                System.out.println("Is Directed -> "+ graph.isDirected());  
                System.out.println("V: " +  graph.getV() + "\nE: " + graph.getE());
                int[][] matrix = graph.getWeightedAdjacencyMatrix();

                System.out.print("\n" + mainMenu());
                opt = Integer.parseInt(read.nextLine());
                char aux1, aux2;

                switch (opt) {

                    case 1: // Adjacency Matrix
                        for (int i = 0; i < matrix.length; i++) {
                            System.out.print(graph.getAdjacencyList()[i].getData()+" ");
                            for (int j = 0; j < matrix.length; j++) {
                                System.out.print(matrix[i][j]+" ");
                            }
                            System.out.println();
                        }
                        break;

                    case 2: // Incidency Matrix
                        int[][] incidencyMatrix = graph.getIncidencyMatrix();
                        for (int i = 0; i < incidencyMatrix.length; i++) {
                            System.out.print(graph.getAdjacencyList()[i].getData()+" ");
                            for (int j = 0; j < incidencyMatrix[0].length; j++) {
                                System.out.print(incidencyMatrix[i][j]+" ");
                            }
                            System.out.println();
                        }
                    break;

                    case 3: //  Adjacency List
                        System.out.println(graph.showAdjacencyList());
                    break;

                    case 4: //Show Graph
                        showGraph pg = new showGraph(graph.getWeightedAdjacencyMatrix(), graph.getVertices());
                        pg.showGraphInterface(matrix);
                    break;

                    case 5: // BFS

                        System.out.println("Ingrese nodo de inicio: ");
                        aux1 = read.nextLine().toCharArray()[0];

                        System.out.println("Ingrese nodo a buscar");
                        aux2 = read.nextLine().toCharArray()[0];

                        System.out.println("\nSearch BFS -> " + graph.BFS(aux1, aux2));
                    break;

                    case 6: // DFS
                        System.out.println("Ingrese nodo de inicio: ");
                        aux1 = read.nextLine().toCharArray()[0];
                        System.out.println("Search DFS -> " + graph.DFS('A'));
                    break;

                    case 7: // DIJKSTRA
                        Object[] dijkstra = graph.dijkstra('A', 'Z');
                        System.out.println(graph.showTable((Object[][][]) dijkstra[0]));
                        System.out.println("\n-> " + dijkstra[1] + " " + dijkstra[2]);
                    break;

                    case 8: // BellMan Ford
                    
                        graph.bellmanFord(matrix, graph.getV(), 0);
                    break;

                    case 0:
                        System.exit(0);
                    break;
                    
                    default:
                        System.out.println("Opcion Incorrecta.");
                    break;
                        
                }
            } catch (Exception e) {

            }
        }
        read.close();
    }

    private static String mainMenu() {

        return """

                [================ Menu de Grafos ================]

                1. Mostrar Matriz de Adyacencia.
                2. Mostrar Matriz de Incidencia.
                3. Mostrar Lista de Adyacencia
                4. Mostrar el Grafo
                5. BFS
                6. DFS
                7. Dijkstra
                8. BellMan-Ford

                0. Salir.
                Opcion: \s""";
        
    }
}