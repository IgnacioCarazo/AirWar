package grafomatriz;

public class Vertice {

    String nombre; //Identificador del vertice(puede omitirse)
    int idDeVertice; // idDeVertice del vertice dentro grafo

    public Vertice(String nombre) {
        this.nombre = nombre;
        this.idDeVertice = -1; //identificar de que no pertenece a ningun grafo(Funciona como ID)
    }

    public String getNombre() {
        return nombre;
    }

    public boolean equals(Vertice vertice) {
        return nombre.equals(vertice.nombre);
    }

    public void setIdDeVertice(int idDeVertice) { //establece el idDeVertice del vertice
        this.idDeVertice = idDeVertice;
    }

    @Override
    public String toString(){
        return "Vertice: Nombre: " + nombre + ", Numero: " + idDeVertice;
    }
}
