import java.util.ArrayList;
import java.util.List;

public class Sala {

    private String codigo;
    private String nombre;
    private int capacidad;
    private EstadoSala estado;
    private ArrayList<Asiento> listaAsientos;

    // Crea la sala y automaticamente llama al metodo para generar sus sillas.
    public Sala(String codigo, String nombre, int filas, int asientosPorFila) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = EstadoSala.DISPONIBLE;
        this.listaAsientos = new ArrayList<>();
        generarAsientos(filas, asientosPorFila);
    }

    // Hace los ciclos para crear las sillas (A1, A2... B1, B2...) y calcula la capacidad total.
    public void generarAsientos(int filas, int asientosPorFila) {
        listaAsientos.clear();
        String letrasFilas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int indice = 0; indice < filas; indice++) {
            char letraFila = letrasFilas.charAt(indice);
            for (int numeroAsiento = 1; numeroAsiento <= asientosPorFila; numeroAsiento++) {
                listaAsientos.add(new Asiento(letraFila, numeroAsiento));
            }
        }
        this.capacidad = listaAsientos.size();
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public EstadoSala getEstado() { return estado; }
    public void setEstado(EstadoSala estado) { this.estado = estado; }
    public List<Asiento> getAsientos() { return listaAsientos; }

    // Pone la sala en servicio.
    public void habilitar() { this.estado = EstadoSala.DISPONIBLE; }

    // Pone la sala fuera de servicio (ej: mantenimiento).
    public void deshabilitar() { this.estado = EstadoSala.FUERA_DE_SERVICIO; }

    // Comprueba si la sala se puede usar.
    public boolean estaDisponible() { return estado == EstadoSala.DISPONIBLE; }

    @Override
    public String toString() {
        return "Sala: " + codigo + " - " + nombre + " | Capacidad: " + capacidad + " | Estado: " + estado;
    }
}