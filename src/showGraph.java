import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;

public class showGraph extends JFrame {
    private int[][] adjacencyMatrix;
    private char[] vertices;

    public showGraph(int[][] AdjacencyMatrix, char[] vertices) {
        this.adjacencyMatrix = AdjacencyMatrix;
        this.vertices = vertices;
        setTitle("Estructura Gráfica de Grafo");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, getLocation().y);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int numVertices = vertices.length;
        int radioCirculo = 100;
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int cuadroSize = 20; // Tamaño del cuadro que muestra el peso
        Font font = new Font("Arial", Font.BOLD, 15);
        Font font1 = new Font("Arial", Font.CENTER_BASELINE, 12);
        
        // Dibujar los vértices como círculos
        for (int i = 0; i < numVertices; i++) {
            double angulo = 2 * Math.PI * (i - 1) / numVertices;
            int x = (int) (centroX + Math.cos(angulo) * radioCirculo);
            int y = (int) (centroY + Math.sin(angulo) * radioCirculo);
            g.setColor(Color.BLACK);
            g.drawOval(x - 25, y - 25, 50, 50); //ponerle borde 
            g.setColor(Color.GRAY);
            g.fillOval( x-25, y-25, 50, 50);
            g.setColor(Color.BLACK);
            g.setFont(font);
            char letra = vertices[i];
            g.drawString(String.valueOf(letra), x - 5, y + 5);
            if (adjacencyMatrix[i][i] != 0) {
                g.setColor(Color.RED);
                g.drawArc(x - 20, y - 20, 40, 40, 45, 280); // Dibujar un arco como bucle
                // Mostrar el peso en un cuadro para el bucle
                String pesoBucle = String.valueOf(adjacencyMatrix[i][i]);
                int posX = x - cuadroSize / 2;
                int posY = y - cuadroSize - 10; // Posición ligeramente más arriba del vértice //
                g.setColor(Color.WHITE);
                g.fillRect(posX, posY, cuadroSize, cuadroSize); // Dibujar el cuadro blanco
                g.setColor(Color.BLACK);
                g.setFont(font1);
                g.drawRect(posX, posY, cuadroSize, cuadroSize); // Dibujar el contorno del cuadro
                g.drawString(pesoBucle, posX + cuadroSize / 2 - 5, posY + cuadroSize / 2 + 5); // Mostrar el peso en el centro del cuadro
            }
        }

        // Dibujar las aristas
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    double anguloI = 2 * Math.PI * (i - 1) / numVertices;
                    double anguloJ = 2 * Math.PI * (j - 1) / numVertices;
                    int x1 = (int) (centroX + Math.cos(anguloI) * radioCirculo);
                    int y1 = (int) (centroY + Math.sin(anguloI) * radioCirculo);
                    int x2 = (int) (centroX + Math.cos(anguloJ) * radioCirculo);
                    int y2 = (int) (centroY + Math.sin(anguloJ) * radioCirculo);
                    g.setColor(Color.RED);
                    g.drawLine(x1, y1, x2, y2);
                    
                    // Mostrar el peso en un cuadro
                    String peso = String.valueOf(adjacencyMatrix[i][j]);
                    int posX = (x1 + x2) / 2 - cuadroSize / 2;
                    int posY = (y1 + y2) / 2 - cuadroSize / 2;
                    g.setFont(font1);
                    g.setColor(Color.WHITE);
                    g.fillRect(posX, posY, cuadroSize, cuadroSize); // Dibujar el cuadro blanco
                    g.setColor(Color.BLACK);
                    g.drawRect(posX, posY, cuadroSize, cuadroSize); // Dibujar el contorno del cuadro
                    g.drawString(peso, posX + cuadroSize / 2 - 5, posY + cuadroSize / 2 + 5); // Mostrar el peso en el centro del cuadro
                }
            }
        }
    }
    
    public void showGraphInterface(int[][] matrizAdyacencia) {
        this.adjacencyMatrix = matrizAdyacencia;
        repaint();
        setVisible(true);
    }
}

