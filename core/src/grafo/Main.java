package grafo;

import grafomatriz.Recorrido;
import grafomatriz.MatrizAdyacencia;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws Exception {
        MatrizAdyacencia grafo = new MatrizAdyacencia(4);

        grafo.agregarArco(0,1, 5);
        grafo.agregarArco(1,0,50);
        grafo.agregarArco(1,3,5);
        grafo.agregarArco(1,2,15);
        grafo.agregarArco(2,0,30);
        grafo.agregarArco(2,3,5);
        grafo.agregarArco(3,0,5);
        grafo.agregarArco(3,2,5);

        System.out.println("Grafo 1\n");
        grafo.printMaAdy();
        System.out.println();

        Recorrido recorridos = new Recorrido(grafo);
        recorridos.obtenerCaminoMasCorto(2,1);

        ArrayList arreglo = recorridos.secuenciaNodos;

        System.out.println("Arreglo con nodos");
        for (int i = 0; i < arreglo.size() ; i++) {
            System.out.println(arreglo.get(i));
        }

        System.out.println("\n\nGrafo 2\n");

        MatrizAdyacencia grafo2 = new MatrizAdyacencia(6);

        grafo2.agregarArco(0,1,3);
        grafo2.agregarArco(0,2,4);
        grafo2.agregarArco(0,4,8);

        grafo2.agregarArco(1,0,4);
        grafo2.agregarArco(1,3,5);
        grafo2.agregarArco(1,4,4);

        grafo2.agregarArco(2,0,7);
        grafo2.agregarArco(2,3,11);
        grafo2.agregarArco(2,4,3);

        grafo2.agregarArco(3,0,1);
        grafo2.agregarArco(3,2,16);
        grafo2.agregarArco(3,5,6);

        grafo2.agregarArco(4,1,1);
        grafo2.agregarArco(4,3,7);
        grafo2.agregarArco(4,5,3);

        grafo2.agregarArco(5,1,4);
        grafo2.agregarArco(5,3,2);
        grafo2.agregarArco(5,4,2);

        grafo2.printMaAdy();


        Recorrido recorridos2 = new Recorrido(grafo2);
        recorridos2.obtenerCaminoMasCorto(0,4);
        recorridos2.obtenerCaminoMasCorto(2,1);
//        recorridos2.obtenerCaminoMasCorto(3,2);
        recorridos2.obtenerCaminoMasCorto(0,5);
//        recorridos2.obtenerCaminoMasCorto(2,3);
        recorridos2.obtenerCaminoMasCorto(5,2);


        System.out.println("\n\nGrafo 3\n");

        MatrizAdyacencia grafo3 = new MatrizAdyacencia(5);

        grafo3.agregarArco(0,1, 1);

        grafo3.agregarArco(1,4,7);
        grafo3.agregarArco(1,3,4);

        grafo3.agregarArco(2,0,3);
        grafo3.agregarArco(2,1,2);
        grafo3.agregarArco(2,4,4);

        grafo3.agregarArco(3,0,6);
        grafo3.agregarArco(3,4,2);

        grafo3.agregarArco(4,3,3);

        grafo3.printMaAdy();

        Recorrido recorrido3 = new Recorrido(grafo3);

        System.out.println("\n\nGrafo 4\n");

        MatrizAdyacencia grafo4 = new MatrizAdyacencia(5);

        grafo4.agregarArco(0,1,5);
        grafo4.agregarArco(0,2,4);

        grafo4.agregarArco(1,0,7);
        grafo4.agregarArco(1,2,1);

        grafo4.agregarArco(2,0,2);
        grafo4.agregarArco(2,4,2);

        grafo4.agregarArco(3,1,5);
        grafo4.agregarArco(3,4,3);

        grafo4.agregarArco(4,1,6);

        Recorrido recorrido4 = new Recorrido(grafo4);


        System.out.println("\n\nGrafo 5\n");

        MatrizAdyacencia grafo5 = new MatrizAdyacencia(7);

        grafo5.agregarArco(0,1,8);
        grafo5.agregarArco(0,2,6);
        grafo5.agregarArco(0,3,2);
        grafo5.agregarArco(0,4,3);

        grafo5.agregarArco(1,0,4);
        grafo5.agregarArco(1,6,7);
        grafo5.agregarArco(1,5,7);
        grafo5.agregarArco(1,4,2);

        grafo5.agregarArco(2,1,4);
        grafo5.agregarArco(2,3,6);
        grafo5.agregarArco(2,6,9);
        grafo5.agregarArco(2,5,3);

        grafo5.agregarArco(3,0,3);
        grafo5.agregarArco(3,5,6);
        grafo5.agregarArco(3,4,2);
        grafo5.agregarArco(3,6,7);

        grafo5.agregarArco(4,5,4);
        grafo5.agregarArco(4,6,13);
        grafo5.agregarArco(4,1,5);
        grafo5.agregarArco(4,0,4);

        grafo5.agregarArco(5,3,13);
        grafo5.agregarArco(5,0,12);
        grafo5.agregarArco(5,4,2);
        grafo5.agregarArco(5,6,2);

        grafo5.agregarArco(6,5,11);
        grafo5.agregarArco(6,2,3);
        grafo5.agregarArco(6,1,7);
        grafo5.agregarArco(6,0,6);

        Recorrido recorrido5 = new Recorrido(grafo5);


        System.out.println("\n\nGrafo 6\n");

        MatrizAdyacencia grafo6 = new MatrizAdyacencia(5);

        grafo6.agregarArco(0,1,5);
        grafo6.agregarArco(0,2,1);
        grafo6.agregarArco(1,3,3);
        grafo6.agregarArco(2,4,2);
        grafo6.agregarArco(3,4,4);

        Recorrido recorrido = new Recorrido(grafo6);


    }





}
