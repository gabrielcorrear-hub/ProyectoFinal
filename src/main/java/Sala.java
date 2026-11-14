import java.util.ArrayList;
import java.util.List;

public class Sala {

    private String codigo;
    private String nombre;
    private int capacidad;
    private EstadoSala estado;
    private ArrayList<Asiento> listaAsientos;

    // Constructor: guarda los datos, deja la sala DISPONIBLE
    // y genera sus asientos a partir de filas y asientos por fila
    public Sala(String codigo, String nombre, int filas, int asientosPorFila) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = EstadoSala.DISPONIBLE;
        this.listaAsientos = new ArrayList<>();
        generarAsientos(filas, asientosPorFila);
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

    // ---------- Funciones ----------

    // Genera los asientos de la sala.
    // Recorre las filas (A, B, C...) y en cada fila crea los asientos 1..asientosPorFila.
    // Al final actualiza la capacidad con la cantidad real de asientos creados.
    public void generarAsientos(int filas, int asientosPorFila) {
        listaAsientos.clear();

        String letrasFilas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < filas; i++) {
            char fila = letrasFilas.charAt(i);
            for (int numero = 1; numero <= asientosPorFila; numero++) {
                listaAsientos.add(new Asiento(fila, numero));
            }
        }

        this.capacidad = listaAsientos.size();
    }

    // Pone la sala en estado DISPONIBLE
    public void habilitar() {
        this.estado = EstadoSala.DISPONIBLE;
    }

    // Pone la sala en estado FUERA_DE_SERVICIO
    public void deshabilitar() {
        this.estado = EstadoSala.FUERA_DE_SERVICIO;
    }

    // Devuelve true si la sala esta DISPONIBLE
    public boolean estaDisponible() {
        return estado == EstadoSala.DISPONIBLE;
    }


    @Override
    public String toString() {
        return "Sala: " + codigo + " - " + nombre
                + "\nCapacidad: " + capacidad
                + "\nEstado: " + estado;
    }
}