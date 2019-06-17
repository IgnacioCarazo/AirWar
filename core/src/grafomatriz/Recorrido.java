package grafomatriz;

import java.util.ArrayList;

public class Recorrido {

    MatrizAdyacencia grafo;
    public int [][] matrizWarshall;
    public int [][] matrizMenoresDistancias;
    public int [][] matrizNodosRecorrido;
    public ArrayList<Integer> secuenciaNodos;

    public Recorrido(MatrizAdyacencia grafo) throws Exception{
        this.secuenciaNodos = new ArrayList<Integer>();
        this.grafo = grafo;
        int n = grafo.numDeVertices;
        this.matrizMenoresDistancias = new int[n][n];
        this.matrizNodosRecorrido = new int[n][n];
        this.matrizWarshall = new int[n][n];
        for (int i = 0; i < matrizNodosRecorrido.length; i++) {
            for (int j = 0; j < matrizNodosRecorrido[0].length; j++) {
                matrizNodosRecorrido[i][j] = j;
                matrizWarshall[i][j] = 0;
            }
        }
        for (int i = 0; i < matrizMenoresDistancias.length ; i++) {
            for (int j = 0; j < matrizMenoresDistancias[0].length ; j++) {
                matrizMenoresDistancias[i][j] = grafo.matrizAdyacente[i][j];
            }
        }
        floyd();
        warshall();
    }

    public void floyd(){
        int contador = 0;
        while(contador < matrizMenoresDistancias.length) {
            for (int i = 0; i < matrizMenoresDistancias.length; i++) {
                for (int j = 0; j < matrizMenoresDistancias[0].length; j++) {
                    if (i == contador || j == contador){
                    } else {
                        int comparable = matrizMenoresDistancias[i][contador] + matrizMenoresDistancias[contador][j];
                        if (matrizMenoresDistancias[i][j] > (comparable)) {
                            matrizMenoresDistancias[i][j] = comparable;
                            matrizNodosRecorrido[i][j] = contador;
                        }else{}
                    }
                }
            }
            contador++;
        }
//        System.out.println("Matriz de nodos para los recorridos");
//        for (int i = 0; i < matrizNodosRecorrido.length; i++) {
//            for (int j = 0; j < matrizNodosRecorrido[0].length; j++) {
//                System.out.print(matrizNodosRecorrido[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//        System.out.println("Matriz de menores distancias");
//        for (int i = 0; i < matrizMenoresDistancias.length ; i++) {
//            for (int j = 0; j <  matrizMenoresDistancias[0].length; j++) {
//                System.out.print(matrizMenoresDistancias[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
    }


    public void warshall() throws Exception
    {
        int n = grafo.numDeVertices;
        int [][] P = new int[n][n]; // matriz de caminos
        // Se obtiene la matriz inicial: matriz de adyacencia
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                P[i][j] = grafo.adyacentes(i,j) ? 1 : 0;
        // se obtienen,a partir de P0, las sucesivas
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    P[i][j] = Math.min(P[i][j] + P[i][k] * P[k][j], 1);
        matrizWarshall = P;
    }

    public ArrayList<Integer> obtenerCaminoMasCorto(int origen, int destino){
        int temporal = 0; // de 2 a 1
        ArrayList<Integer> secuencia = new ArrayList<Integer>();
        secuencia.add(origen);
        while(matrizNodosRecorrido[origen][destino]!=destino) {
            secuencia.add(matrizNodosRecorrido[origen][destino]);
            origen = matrizNodosRecorrido[origen][destino];
        }
        secuencia.add(destino);
        return secuencia;
    }
}
