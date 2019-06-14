package grafomatriz;

public class MatrizAdyacencia { //define la matriz de adyacencia

    int numDeVertices; //contador para meter los vertices en el arreglo de vertices
    static int MAX_Vertices = 20; //idDeVertice maximo por grafo
    int numBase = 1000;
    public int [][] matrizAdyacente; //matriz
    Vertice[] vertices; //Arreglo de vertices


    public MatrizAdyacencia() {
        this(MAX_Vertices);
    }

    //   _________________________
    //__/Constructor de la matriz \_____
    //  \_________________________/
    public MatrizAdyacencia(int cantidadVertices) {
        matrizAdyacente = new int[cantidadVertices][cantidadVertices];
        vertices = new Vertice[cantidadVertices];
        for (int i = 0; i < cantidadVertices; i++) {
            for (int j = 0; j < cantidadVertices; j++) {
                if(i==j){
                    matrizAdyacente[i][j] = 0;
                }else {
                    matrizAdyacente[i][j] = numBase;
                }
            }
        }
        numDeVertices = 0;
        for (int i = 0; i < cantidadVertices ; i++) {
            agregarVertice("base"+i);
        }
    }

    //      _______________
    //_____/Añadir vertices\______
    //     \_______________/
    public void agregarVertice(String nombre){
        boolean esta = idDeVertice(nombre) >= 0;
        if (!esta) {
            Vertice v = new Vertice(nombre);
            v.setIdDeVertice(numDeVertices);
            vertices[numDeVertices++] = v;
        }
    }
    //     ________________________________________
    //____/retorna el id del vertice en el arreglo \_______
    //    \________________________________________/
    public int idDeVertice(String nombre) {
        boolean encontrado = false;
        Vertice v = new Vertice(nombre);
        int i = 0;
        for (; (i < numDeVertices && !encontrado);) {
            encontrado = vertices[i].equals(v);
            if (!encontrado)
                i++; //para seguir buscando
        }
        return (i<numDeVertices)? vertices[i].idDeVertice : -1;
    }

//    //____/Anadir un arco con peso
//    public void agregarArco(String vertive1,String vertice2, int peso) throws Exception{
//        int vert1, vert2;
//        vert1 = idDeVertice(vertive1);
//        vert2 = idDeVertice(vertice2);
//        if(vert1 < 0 || vert2 < 0) throw new Exception("Vertice no existe");
//        matrizAdyacente[vert1][vert2] = peso;
//        matrizAdyacente[vert2][vert1] = peso; //agregar si se quiere sea no dirigido
//
//    }

    //forma dos (recibir los numeros de los vertices) Para grafos valorados este metodo recibe el peso
    public void agregarArco(int vertice1,int vertice2, int peso) throws Exception{
        if(vertice1 < 0 || vertice2 < 0) throw new Exception("Vertice no existe");
        matrizAdyacente[vertice1][vertice2] = peso;
//        matrizAdyacente[vertice2][vertice1] = peso;
    }

    //_________/Determina adyacencia por medio del nombre del vertice
    public boolean adyacentes(String vertice1,String vertice2)throws Exception{
        int vert1, vert2;
        vert1 = idDeVertice(vertice1);
        vert2 = idDeVertice(vertice2);
        if(vert1<0 || vert2 < 0) throw new Exception("Vertice no existe");
        return matrizAdyacente[vert1][vert2] != numBase;
    }

//    public int getPeso(String vertice1, String vertice2) throws Exception {
//        if(adyacentes(vertice1,vertice2)){
//            return matrizAdyacente[idDeVertice(vertice1)][idDeVertice(vertice2)];
//        }
//        return numBase;
//    }

    public int numeroDeVertices() {
        return numDeVertices;
    }

    //     ____________________________________________________
    //____/Determina adyacencia entre vertices por medio del ID\________
    //    \____________________________________________________/

    public boolean adyacentes(int vertice1,int vertice2) throws Exception{
        if(vertice1 < 0 || vertice2 < 0) throw new Exception("Vertice no existe");
        return matrizAdyacente[vertice1][vertice2] != numBase;
    }


    public void printMaAdy(){
        System.out.println("Matriz de adyacencia");
        for (int i = 0; i < matrizAdyacente.length ; i++) {
            for (int j = 0; j < matrizAdyacente[0].length; j++) {
                System.out.print(matrizAdyacente[i][j] + " ");
            }
            System.out.println();
        }
    }
}
