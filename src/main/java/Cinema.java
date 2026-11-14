import java.util.ArrayList;
import java.util.List;

public class Cinema {

    private String nombre;
    private ArrayList<Pelicula> listaPeliculas;
    private ArrayList<Sala> listaSalas;
    private ArrayList<Funcion> listaFunciones;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Venta> listaVentas;

    public Cinema(String nombre) {
        this.nombre = nombre;
        listaPeliculas = new ArrayList<>();
        listaSalas = new ArrayList<>();
        listaFunciones = new ArrayList<>();
        listaClientes = new ArrayList<>();
        listaVentas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Venta> getVentas() {
        return listaVentas;
    }
}
