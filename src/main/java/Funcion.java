import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Funcion {

    private String codigo;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate fecha;
    private LocalTime hora;
    private double valorBase;
    private EstadoFuncion estado;
    private ArrayList<Asiento> listaAsientos;

    public Funcion(String codigo, Pelicula pelicula, Sala sala, LocalDate fecha, LocalTime hora, double valorBase) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.valorBase = valorBase;
        this.estado = EstadoFuncion.PROGRAMADA;

        this.listaAsientos = new ArrayList<>();
        // Copia independiente de los asientos de la sala
        for (Asiento asientoDeLaSala : sala.getAsientos()) {
            this.listaAsientos.add(asientoDeLaSala.copiar());
        }
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Pelicula getPelicula() { return pelicula; }
    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public double getValorBase() { return valorBase; }
    public void setValorBase(double valorBase) { this.valorBase = valorBase; }
    public EstadoFuncion getEstado() { return estado; }
    public void setEstado(EstadoFuncion estado) { this.estado = estado; }
    public List<Asiento> getAsientos() { return listaAsientos; }

    //  Métodos de Asientos

    public Asiento buscarAsiento(String codigoAsiento) {
        for (Asiento asientoDeLaFuncion : listaAsientos) {
            if (asientoDeLaFuncion.getCodigo().equals(codigoAsiento)) {
                return asientoDeLaFuncion;
            }
        }
        return null;
    }

    // Ocupa el asiento si está disponible y lo devuelve. Si no, devuelve null.
    public Asiento ocuparAsiento(String codigoAsiento) {
        Asiento asiento = buscarAsiento(codigoAsiento);
        if (asiento != null && asiento.estaDisponible()) {
            asiento.ocupar();
            return asiento;
        }
        return null;
    }

    // Libera el asiento (lo pone DISPONIBLE)
    public void liberarAsiento(String codigoAsiento) {
        Asiento asiento = buscarAsiento(codigoAsiento);
        if (asiento != null) {
            asiento.liberar();
        }
    }

    public List<Asiento> obtenerAsientosDisponibles() {
        List<Asiento> disponibles = new ArrayList<>();
        for (Asiento asientoDeLaFuncion : listaAsientos) {
            if (asientoDeLaFuncion.estaDisponible()) {
                disponibles.add(asientoDeLaFuncion);
            }
        }
        return disponibles;
    }

    public int contarDisponibles() {
        return obtenerAsientosDisponibles().size();
    }

    public int contarOcupados() {
        return listaAsientos.size() - contarDisponibles();
    }

    public boolean hayAsientosDisponibles() {
        return contarDisponibles() > 0;
    }

    // --- Métodos de Negocio ---

    public double calcularValor(TipoCliente tipoCliente) {
        return this.valorBase + tipoCliente.getValor();
    }

    public void cancelar() {
        this.estado = EstadoFuncion.CANCELADA;
    }

    public void finalizar() {
        this.estado = EstadoFuncion.FINALIZADA;
    }

    public boolean estaProgramada() {
        return estado == EstadoFuncion.PROGRAMADA;
    }

    @Override
    public String toString() {
        return "Funcion: " + codigo + " | Pelicula: " + pelicula.getNombre()
                + " | Sala: " + sala.getNombre() + " | Disponibles: " + contarDisponibles();
    }
}