import java.util.ArrayList;
import java.util.List;

public class Sala {

    private String codigo;
    private String nombre;
    private int capacidad;
    private EstadoSala estado;
    private ArrayList<Asiento> listaAsientos;

    public Sala(String codigo, String nombre, int filas, int asientosPorFila) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = EstadoSala.DISPONIBLE;
        this.listaAsientos = new ArrayList<>();
        generarAsientos(filas, asientosPorFila);
    }

    private void generarAsientos(int filas, int asientosPorFila) {
        listaAsientos.clear();

        for (char fila = 'A'; fila < 'A' + filas; fila++) {
            for (int numero = 1; numero <= asientosPorFila; numero++) {
                listaAsientos.add(new Asiento(fila, numero));
            }
        }

        this.capacidad = listaAsientos.size();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }

    public List<Asiento> getAsientos() {
        return listaAsientos;
    }
}