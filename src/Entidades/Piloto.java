package Entidades;

public class Piloto {
    private String nombre;
    private int contador;

    public Piloto(String nombre, int contador) {
        this.nombre = nombre;
        this.contador = contador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getContador() {
        return contador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
}
